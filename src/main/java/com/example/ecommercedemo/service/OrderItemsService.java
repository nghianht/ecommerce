package com.example.ecommercedemo.service;

import com.example.ecommercedemo.dto.OrderItemsDto;
import com.example.ecommercedemo.entity.CartsItems;
import com.example.ecommercedemo.entity.OrderItems;
import com.example.ecommercedemo.entity.Orders;

import java.util.List;

public interface OrderItemsService {
    OrderItems addOrderItemsInOrders(Orders orders, CartsItems cartsItems);
    List<OrderItemsDto> showOrderItemsInOrders(Long ordersId);
}
