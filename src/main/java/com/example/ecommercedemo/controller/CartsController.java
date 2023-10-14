package com.example.ecommercedemo.controller;

import com.example.ecommercedemo.dto.CartsDto;
import com.example.ecommercedemo.dto.ProductDto;
import com.example.ecommercedemo.service.CartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartsController {
    private CartsService cartsService;
    @Autowired
     public CartsController(CartsService cartsService) {
        this.cartsService = cartsService;
    }
    @PostMapping("/{cartsId}/user/add-product/{productId}")
    public ResponseEntity<CartsDto> userAddProductToCart(@PathVariable("cartsId") long cartsId,
                                                         @PathVariable("productId") long productId){
        CartsDto cartsDto = cartsService.userAddProductToCart(cartsId, productId);
        return new ResponseEntity<>(cartsDto, HttpStatus.CREATED);
    }
    @GetMapping("/{cartsId}/user/{userId}")
    public ResponseEntity<CartsDto> userGetCart(@PathVariable("cartsId") long cartsId){
        CartsDto cartsDto = cartsService.getCartByUserId(cartsId);
        return new ResponseEntity<>(cartsDto, HttpStatus.OK);
    }
    @PostMapping("/{cartsId}/user/drop-product/{productId}")
    public ResponseEntity<CartsDto> userDropProductInCart(@PathVariable("cartsId") long cartsId,
                                                          @PathVariable("productId") long productId){
        CartsDto cartsDto = cartsService.dropProductInCart(cartsId, productId);
        return new ResponseEntity<>(cartsDto, HttpStatus.OK);
    }

}
