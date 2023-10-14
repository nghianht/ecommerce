package com.example.ecommercedemo.repo;

import com.example.ecommercedemo.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Orders,Long> {
    List<Orders> findByUserId(Long userId);
}
