package com.example.ecommercedemo.service;

import com.example.ecommercedemo.dto.CartsDto;
import com.example.ecommercedemo.dto.ProductDto;
import com.example.ecommercedemo.entity.User;

public interface CartsService {
    CartsDto userAddProductToCart(long cartsId,long productId);
    CartsDto getCartByUserId(long cartId);
    CartsDto dropProductInCart(long cartId, long productId);
    void dropAllCartItemsInCart(long cartsId);
    void addCart(User user);
}
