package com.example.ecommercedemo.service;

import com.example.ecommercedemo.dto.ProductDto;
import com.example.ecommercedemo.entity.CartsItems;

public interface CartsItemsService {
    CartsItems addProductToCartsItemsByCartsId(long cartsId, long productId);
    void dropProductInCartItemsByProductId(long cartsId, long productId);
    void deleteCartsItemByCartsId(long cartsId);
}
