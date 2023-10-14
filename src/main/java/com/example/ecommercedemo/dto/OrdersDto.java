package com.example.ecommercedemo.dto;

import com.example.ecommercedemo.entity.OrderItems;
import com.example.ecommercedemo.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {
    private Long id;
    private Long userId;
    private Integer totalPrice;
    private List<OrderItemsDto> orderItemsDtoList;
    private Date createdAt;
    private String userAddress;
}
