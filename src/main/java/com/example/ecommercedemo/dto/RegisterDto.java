package com.example.ecommercedemo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
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
}
