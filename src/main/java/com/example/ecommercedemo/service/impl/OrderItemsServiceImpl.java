package com.example.ecommercedemo.service.impl;

import com.example.ecommercedemo.dto.OrderItemsDto;
import com.example.ecommercedemo.entity.CartsItems;
import com.example.ecommercedemo.entity.OrderItems;
import com.example.ecommercedemo.entity.Orders;
import com.example.ecommercedemo.repo.OrderItemsRepo;
import com.example.ecommercedemo.repo.OrdersRepo;
import com.example.ecommercedemo.service.OrderItemsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemsServiceImpl implements OrderItemsService {
    private OrderItemsRepo orderItemsRepo;

    private ModelMapper mapper;
    @Autowired
    public OrderItemsServiceImpl(OrderItemsRepo orderItemsRepo, ModelMapper mapper) {
        this.orderItemsRepo = orderItemsRepo;
        this.mapper = mapper;
    }
    @Override
    public OrderItems addOrderItemsInOrders(Orders orders, CartsItems cartsItems) {
        OrderItems orderItems = new OrderItems();
        orderItems.setOrders(orders);
        orderItems.setProduct(cartsItems.getProduct());
        orderItems.setQuantity(cartsItems.getQuantity());
        orderItems.setTotalPrice(cartsItems.getTotalPrice());
        return orderItems;
    }
    @Override
    public List<OrderItemsDto> showOrderItemsInOrders(Long ordersId) {
        List<OrderItems> orderItemsList = orderItemsRepo.findByOrdersId(ordersId);
        return orderItemsList.stream().map(orderItems->mapper.map(orderItems, OrderItemsDto.class)).collect(Collectors.toList());
    }
}
