package com.example.ecommercedemo.dto;
import com.example.ecommercedemo.entity.Orders;
import com.example.ecommercedemo.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class UserDto {
    private Long id;
    private String username;
    private String name;
    @Email
    private String email;
    private String address;
    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 10, max = 10, message = "So dien thoai khong hop le")
    @Pattern(regexp = "^[0-9]{10}$", message = "So dien thoai khong hop le")
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    private String password;
    private Set<Role> roles;
    private List<Orders> Orders;
    private Date createdAt;
}
