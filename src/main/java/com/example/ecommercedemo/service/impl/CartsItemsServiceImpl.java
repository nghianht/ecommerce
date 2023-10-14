package com.example.ecommercedemo.service.impl;

import com.example.ecommercedemo.entity.CartsItems;
import com.example.ecommercedemo.entity.Product;
import com.example.ecommercedemo.exception.ResourceNotFoundException;
import com.example.ecommercedemo.repo.CartsItemsRepo;
import com.example.ecommercedemo.repo.CartsRepo;
import com.example.ecommercedemo.repo.ProductRepo;
import com.example.ecommercedemo.service.CartsItemsService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class CartsItemsServiceImpl implements CartsItemsService {
    private CartsItemsRepo cartsItemsRepo;
    private  CartsRepo cartsRepo;
    private ProductRepo productRepo;
    private ModelMapper mapper;

    @Autowired
    public CartsItemsServiceImpl(CartsItemsRepo cartsItemsRepo, ModelMapper mapper,CartsRepo cartsRepo, ProductRepo productRepo) {
        this.cartsItemsRepo = cartsItemsRepo;
        this.mapper = mapper;
        this.cartsRepo =  cartsRepo;
        this.productRepo = productRepo;
    }

    @Override
    public CartsItems addProductToCartsItemsByCartsId(long cartsId, long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() ->new ResourceNotFoundException("product","id", productId));
        CartsItems cartsItems = new CartsItems();
        if (!cartsItemsRepo.existsByCartsId(cartsId)) {
            cartsItems.setCarts(cartsRepo.findById(cartsId).orElseThrow(() -> new ResourceNotFoundException("Carts", "id", cartsId)));
            cartsItems.setQuantity(1);
            cartsItems.setProduct(product);
            cartsItems.setTotalPrice(product.getPrice());
        }
        else {
            List<Long> productIdList = cartsItemsRepo.findProductIdsByCartsId(cartsId);
            if (!productIdList.contains(productId)){
                cartsItems.setCarts(cartsRepo.findById(cartsId).orElseThrow(()->new ResourceNotFoundException("Carts", "id", cartsId)));
                cartsItems.setQuantity(1);
                cartsItems.setProduct(product);
                cartsItems.setTotalPrice(product.getPrice());

            }
            else {
                cartsItems = cartsItemsRepo.findByCartsIdAndProductId(cartsId, productId);
                int oldQuantity = cartsItems.getQuantity();
                cartsItems.setQuantity(oldQuantity+1);
                cartsItems.setTotalPrice(product.getPrice()*(oldQuantity+1));
            }
        }
        cartsItemsRepo.save(cartsItems);
        return cartsItems;
    }
    @Override
    public void dropProductInCartItemsByProductId(long cartsId, long productId){
        CartsItems cartsItems = cartsItemsRepo.findByCartsIdAndProductId(cartsId,productId);
        if (cartsItems.getQuantity() == 1){
            cartsItemsRepo.delete(cartsItems);
        }
        else {
            int newQuantity = cartsItems.getQuantity() - 1;
            cartsItems.setQuantity(newQuantity);
            cartsItems.setTotalPrice(newQuantity*cartsItems.getProduct().getPrice());
            cartsItemsRepo.save(cartsItems);
        }
    }

    @Override
    @Transactional
    public void deleteCartsItemByCartsId(long cartsId) {
        cartsItemsRepo.deleteCartsItemByCartsId(cartsId);
    }
}
