package com.example.ecommercedemo.service.auth.impl;

import com.example.ecommercedemo.dto.LoginDto;
import com.example.ecommercedemo.dto.RegisterDto;
import com.example.ecommercedemo.entity.Role;
import com.example.ecommercedemo.entity.User;
import com.example.ecommercedemo.exception.ApiException;
import com.example.ecommercedemo.jwt.JwtAuthenticationFilter;
import com.example.ecommercedemo.jwt.JwtTokenProvider;
import com.example.ecommercedemo.repo.RoleRepo;
import com.example.ecommercedemo.repo.UserRepo;
import com.example.ecommercedemo.service.CartsService;
import com.example.ecommercedemo.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    private JwtAuthenticationFilter authenticationFilter;
    private JwtTokenProvider tokenProvider;
    private CartsService cartsService;
    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepo userRepo, RoleRepo roleRepo,
                           PasswordEncoder passwordEncoder,
                           JwtAuthenticationFilter authenticationFilter,
                           JwtTokenProvider tokenProvider,
                           CartsService cartsService) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationFilter = authenticationFilter;
        this.tokenProvider = tokenProvider;
        this.cartsService = cartsService;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(), loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        //kiểm tra username có tồn tại khoong
        if (userRepo.existsByUsername(registerDto.getUsername())){
            throw  new ApiException("Thông tin không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        if (userRepo.existsByEmail(registerDto.getEmail())){
            throw new ApiException("Thông tin không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        if (userRepo.existsByEmail(registerDto.getPhoneNumber())){
            throw new ApiException("Thông tin không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setAddress(registerDto.getAddress());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepo.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepo.save(user);
        cartsService.addCart(user);
        return "Dang ky thanh cong";
    }
}
