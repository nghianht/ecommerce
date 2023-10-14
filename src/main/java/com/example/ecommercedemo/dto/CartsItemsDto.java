package com.example.ecommercedemo.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartsItemsDto {
    private Long id;
    private Long cartsId;
    private Long productId;
    private int quantity;
    private Integer totalPrice;
}
