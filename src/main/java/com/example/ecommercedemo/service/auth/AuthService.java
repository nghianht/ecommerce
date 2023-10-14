package com.example.ecommercedemo.service.auth;

import com.example.ecommercedemo.dto.LoginDto;
import com.example.ecommercedemo.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}