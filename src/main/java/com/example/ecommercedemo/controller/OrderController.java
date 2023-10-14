package com.example.ecommercedemo.controller;

import com.example.ecommercedemo.dto.OrderItemsDto;
import com.example.ecommercedemo.dto.OrdersDto;
import com.example.ecommercedemo.service.CartsService;
import com.example.ecommercedemo.service.OrderItemsService;
import com.example.ecommercedemo.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/orders")
public class OrderController {
    private OrdersService ordersService;
    private CartsService cartsService;
    private OrderItemsService orderItemsService;
    @Autowired
    public OrderController(OrdersService ordersService, CartsService cartsService, OrderItemsService orderItemsService) {
        this.ordersService = ordersService;
        this.cartsService = cartsService;
        this.orderItemsService = orderItemsService;
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrdersDto>> getOrdersByUserId(@PathVariable("userId") long userId){
        List<OrdersDto> ordersDtoList = ordersService.getAllOrdersByUserId(userId);
        return new ResponseEntity<>(ordersDtoList, HttpStatus.OK);
    }
    @PostMapping("/user/{userId}/place-order")
    public ResponseEntity<OrdersDto> createOrder(@PathVariable("userId") long userId){
        OrdersDto ordersDto = ordersService.createOrders(userId);
        return new ResponseEntity<>(ordersDto, HttpStatus.CREATED);
    }
    @GetMapping("{ordersId}")
    public ResponseEntity<List<OrderItemsDto>> getOrdersItemInOneOrder(@PathVariable("ordersId") Long ordersId){
        List<OrderItemsDto> orderItemsDtoList = orderItemsService.showOrderItemsInOrders(ordersId);
        return new ResponseEntity<>(orderItemsDtoList, HttpStatus.OK);
    }
}
