package com.example.ecommercedemo.service.impl;

import com.example.ecommercedemo.dto.CartsDto;
import com.example.ecommercedemo.dto.CartsItemsDto;
import com.example.ecommercedemo.dto.ProductDto;
import com.example.ecommercedemo.entity.Carts;
import com.example.ecommercedemo.entity.CartsItems;
import com.example.ecommercedemo.entity.Product;
import com.example.ecommercedemo.entity.User;
import com.example.ecommercedemo.exception.ResourceNotFoundException;
import com.example.ecommercedemo.repo.CartsItemsRepo;
import com.example.ecommercedemo.repo.CartsRepo;
import com.example.ecommercedemo.repo.ProductRepo;
import com.example.ecommercedemo.service.CartsItemsService;
import com.example.ecommercedemo.service.CartsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartsServiceImpl implements CartsService {
    private CartsRepo cartsRepo;
    private CartsItemsRepo cartsItemsRepo;
    private CartsItemsService cartsItemsService;
    private ProductRepo productRepo;
    private ModelMapper mapper;
    @Autowired
    public CartsServiceImpl(CartsRepo cartsRepo,
                            CartsItemsRepo cartsItemsRepo,
                            CartsItemsService cartsItemsService,
                            ProductRepo productRepo,
                            ModelMapper mapper) {
        this.cartsRepo = cartsRepo;
        this.cartsItemsRepo = cartsItemsRepo;
        this.cartsItemsService = cartsItemsService;
        this.productRepo =  productRepo;
        this.mapper = mapper;
    }
    private Integer calculateTotalPrice(List<CartsItems> cartsItemsList) {
        Integer total = 0;
        for (CartsItems cartsItems : cartsItemsList) {
            total = total+ cartsItems.getTotalPrice();
        }
        return total;
    }

    @Override
    public CartsDto userAddProductToCart(long cartsId, long productId) {
        Carts carts = cartsRepo.findById(cartsId).orElseThrow(()->new ResourceNotFoundException("Cart", "id", cartsId));
        List<CartsItems> cartsItemsList = carts.getCartItems();

        CartsItems cartsItems = cartsItemsService.addProductToCartsItemsByCartsId(cartsId, productId);
        if (!cartsItemsList.contains(cartsItems)){
            cartsItemsList.add(cartsItems);
        }
        carts.setCartItems(cartsItemsList);
        carts.setCartTotal(calculateTotalPrice(cartsItemsList));
        cartsRepo.save(carts);
        // Ánh xạ danh sách cartsItems thành danh sách cartsItemsDto
        List<CartsItemsDto> cartsItemsDtoList = cartsItemsList.stream()
                .map(item -> mapper.map(item, CartsItemsDto.class))
                .collect(Collectors.toList());
        CartsDto cartsDto = new CartsDto();
        cartsDto.setCartId(carts.getCartId());
        cartsDto.setUserId(carts.getUser().getId());
        cartsDto.setCartTotal(carts.getCartTotal());
        cartsDto.setCartsItemsDtoList(cartsItemsDtoList);
        return cartsDto;
    }
    @Override
    public CartsDto getCartByUserId(long cartsId) {
        Carts carts = cartsRepo.findById(cartsId).orElseThrow(() -> new ResourceNotFoundException("Carts", "id", cartsId));
        List<CartsItems> cartsItemsList = carts.getCartItems();
        List<CartsItemsDto> cartsItemsDtoList = cartsItemsList.stream()
                .map(item -> mapper.map(item, CartsItemsDto.class))
                .collect(Collectors.toList());
        CartsDto cartsDto = new CartsDto();
        cartsDto.setCartId(carts.getCartId());
        cartsDto.setUserId(carts.getUser().getId());
        cartsDto.setCartTotal(carts.getCartTotal());
        cartsDto.setCartsItemsDtoList(cartsItemsDtoList);
        return cartsDto;
    }

    @Override
    public CartsDto dropProductInCart(long cartsId, long productId) {
        cartsItemsService.dropProductInCartItemsByProductId(cartsId,productId);
        Carts carts = cartsRepo.findById(cartsId).orElseThrow(() -> new ResourceNotFoundException("Carts", "id", cartsId));
        List<CartsItems> cartsItemsList = carts.getCartItems();
        carts.setCartTotal(calculateTotalPrice(cartsItemsList));
        cartsRepo.save(carts);
        List<CartsItemsDto> cartsItemsDtoList = cartsItemsList.stream()
                .map(item -> mapper.map(item, CartsItemsDto.class))
                .collect(Collectors.toList());
        CartsDto cartsDto = new CartsDto();
        cartsDto.setCartId(carts.getCartId());
        cartsDto.setUserId(carts.getUser().getId());
        cartsDto.setCartTotal(carts.getCartTotal());
        cartsDto.setCartsItemsDtoList(cartsItemsDtoList);
        return cartsDto;
    }

    @Override
    public void dropAllCartItemsInCart(long cartsId) {
        Carts carts = cartsRepo.findById(cartsId).orElseThrow(()->new ResourceNotFoundException("Cart", "id",cartsId));
        carts.setCartItems(new ArrayList<>());
        cartsRepo.save(carts);
    }

    @Override
    public void addCart(User user) {
        Carts carts = new Carts();
        carts.setUser(user);
        carts.setCartTotal(0);
        cartsRepo.save(carts);
    }
}
