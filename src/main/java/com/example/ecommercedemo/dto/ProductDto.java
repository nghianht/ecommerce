package com.example.ecommercedemo.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotEmpty
    @Size(min = 3, message = "Tên không được để trống")
    private String name;
    @NotEmpty
    @Size(min = 3, message = "Mô tả phải trên 3 ký tự")
    private String description;
    @NotNull(message = "Giá không được để trống")
    private int price;
    private Long categoryId;
    private Date createdAt;
}
