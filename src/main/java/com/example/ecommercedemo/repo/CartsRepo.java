package com.example.ecommercedemo.repo;

import com.example.ecommercedemo.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartsRepo extends JpaRepository<Carts, Long> {
}
