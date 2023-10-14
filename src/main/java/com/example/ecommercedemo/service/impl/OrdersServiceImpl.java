package com.example.ecommercedemo.service.impl;
import com.example.ecommercedemo.dto.OrderItemsDto;
import com.example.ecommercedemo.dto.OrdersDto;
import com.example.ecommercedemo.entity.*;
import com.example.ecommercedemo.exception.ResourceNotFoundException;
import com.example.ecommercedemo.repo.*;
import com.example.ecommercedemo.service.CartsItemsService;
import com.example.ecommercedemo.service.CartsService;
import com.example.ecommercedemo.service.OrderItemsService;
import com.example.ecommercedemo.service.OrdersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {
    private OrdersRepo ordersRepo;
    private OrderItemsRepo orderItemsRepo;
    private CartsItemsRepo cartsItemsRepo;
    private CartsRepo cartsRepo;
    private ModelMapper mapper;
    private UserRepo userRepo;
    private OrderItemsService orderItemsService;
    private CartsService cartsService;
    private CartsItemsService cartsItemsService;
    @Autowired

    public OrdersServiceImpl(OrdersRepo ordersRepo,
                             OrderItemsRepo orderItemsRepo,
                             CartsItemsRepo cartsItemsRepo,
                             CartsRepo cartsRepo,
                             ModelMapper mapper,
                             UserRepo userRepo,
                             OrderItemsService orderItemsService,
                             CartsService cartsService,
                             CartsItemsService cartsItemsService) {
        this.ordersRepo = ordersRepo;
        this.orderItemsRepo = orderItemsRepo;
        this.cartsItemsRepo = cartsItemsRepo;
        this.cartsRepo = cartsRepo;
        this.mapper = mapper;
        this.userRepo = userRepo;
        this.orderItemsService = orderItemsService;
        this.cartsService = cartsService;
        this.cartsItemsService = cartsItemsService;
    }
    //hàm tạo 1 order mới
    //một khi vào giỏ hàng nhấn thanh toán, sẽ tạo ra 1 order lấy tất cả sản phẩm trong cart
    //và tồng giá tiền của cart đó
    @Override
    public OrdersDto createOrders(Long userId) {
        Orders orders = new Orders();
        //lấy ra người dùng và cart để trích xuất thông tin
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        Carts carts = cartsRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Cart", "id", userId));
        //thêm các giá trị vào cho orders
        orders.setUser(user);
        orders.setTotalPrice(carts.getCartTotal());
        orders.setAddress(user.getAddress());
        List<OrderItems> orderItemsList = new ArrayList<>();
        List<CartsItems> cartsItemsList = carts.getCartItems();
        for (CartsItems cartsItems: cartsItemsList)
        {
            OrderItems orderItems = orderItemsService.addOrderItemsInOrders(orders,cartsItems);
            orderItemsList.add(orderItems);
        }
        orders.setOrderItemsList(orderItemsList);
        //lưu order vào db
        ordersRepo.save(orders);
        //chuyển về orderDTO để trả cho người dùng
        OrdersDto ordersDto = new OrdersDto();
        List<OrderItemsDto> ordersDtoList = orderItemsList.stream()
                .map(item -> mapper.map(item, OrderItemsDto.class))
                .collect(Collectors.toList());
        ordersDto.setCreatedAt(orders.getCreatedAt());
        ordersDto.setId(orders.getId());
        ordersDto.setTotalPrice(orders.getTotalPrice());
        ordersDto.setUserId(orders.getUser().getId());
        ordersDto.setUserAddress(orders.getUser().getAddress());
        ordersDto.setOrderItemsDtoList(ordersDtoList);
        cartsService.dropAllCartItemsInCart(userId);
        cartsItemsService.deleteCartsItemByCartsId(userId);
        return ordersDto;

    }

    @Override
    public List<OrdersDto> getAllOrdersByUserId(Long userId) {
        List<Orders> ordersList = ordersRepo.findByUserId(userId);
        List<OrdersDto> ordersDtoList = new ArrayList<>();
        for (Orders orders:ordersList
             ) {
            List<OrderItems> orderItemsList = orders.getOrderItemsList();
            OrdersDto ordersDto = new OrdersDto();
            List<OrderItemsDto> ordersItemsDtoList = orderItemsList.stream()
                    .map(item -> mapper.map(item, OrderItemsDto.class))
                    .collect(Collectors.toList());
            ordersDto.setCreatedAt(orders.getCreatedAt());
            ordersDto.setId(orders.getId());
            ordersDto.setTotalPrice(orders.getTotalPrice());
            ordersDto.setUserId(orders.getUser().getId());
            ordersDto.setUserAddress(orders.getUser().getAddress());
            ordersDto.setOrderItemsDtoList(ordersItemsDtoList);
            ordersDtoList.add(ordersDto);
        }
        return ordersDtoList;
    }
    @Override
    public List<OrderItemsDto> getOneOrderByUserId() {
        return null;
    }
}
