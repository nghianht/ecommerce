package com.example.ecommercedemo.dto;
import lombok.*;
import java.math.BigInteger;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerOrderDto {
    private Long id;
    private Long userId;
    private BigInteger totalAmount;
    private Date createdAt;
}
