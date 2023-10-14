package com.example.ecommercedemo.repo;

import com.example.ecommercedemo.entity.CartsItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartsItemsRepo extends JpaRepository<CartsItems, Long> {
    @Query("SELECT COUNT(ci) > 0 FROM CartsItems ci WHERE ci.carts.cartId = :cartsId")
    boolean existsByCartsId(@Param("cartsId") Long cartsId);
    @Query("SELECT c.product.id FROM CartsItems c WHERE c.carts.id = :cartsId")
    List<Long> findProductIdsByCartsId(@Param("cartsId") Long cartsId);
    @Query("SELECT c FROM CartsItems c WHERE c.carts.id = :cartsId AND c.product.id = :productId")
    CartsItems findByCartsIdAndProductId(@Param("cartsId") Long cartsId, @Param("productId") Long productId);
    @Modifying
    @Query("DELETE FROM CartsItems c WHERE c.carts.id = :cartsId")
    void deleteCartsItemByCartsId(@Param("cartsId") long cartsId);
}
