package com.example.ecommercedemo.dto;

import com.example.ecommercedemo.entity.Orders;
import com.example.ecommercedemo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigInteger;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemsDto {
    private Long id;
    private String ordersId;
    private String productId;
    private Integer quantity;
    private Integer totalPrice;
}
