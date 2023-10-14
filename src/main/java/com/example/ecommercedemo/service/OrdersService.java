package com.example.ecommercedemo.service;

import com.example.ecommercedemo.dto.OrderItemsDto;
import com.example.ecommercedemo.dto.OrdersDto;

import java.util.List;

public interface OrdersService {
    OrdersDto createOrders(Long userId);
    List<OrdersDto> getAllOrdersByUserId(Long userId);
    List<OrderItemsDto> getOneOrderByUserId();
}
