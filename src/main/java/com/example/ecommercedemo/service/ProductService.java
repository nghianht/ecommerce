package com.example.ecommercedemo.service;

import com.example.ecommercedemo.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProductById(long id,ProductDto productDto);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();
    void deleteProductById(long id);
    List<ProductDto> findProductsByCategoryId(long id);
}
