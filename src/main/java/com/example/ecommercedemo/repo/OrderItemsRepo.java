package com.example.ecommercedemo.repo;

import com.example.ecommercedemo.entity.Orders;
import com.example.ecommercedemo.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepo extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findByOrdersId(Long ordersId);
}
