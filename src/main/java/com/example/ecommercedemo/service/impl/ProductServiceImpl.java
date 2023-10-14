package com.example.ecommercedemo.service.impl;

import com.example.ecommercedemo.dto.ProductDto;
import com.example.ecommercedemo.entity.Category;
import com.example.ecommercedemo.entity.Product;
import com.example.ecommercedemo.exception.ResourceNotFoundException;
import com.example.ecommercedemo.repo.CategoryRepo;
import com.example.ecommercedemo.repo.ProductRepo;
import com.example.ecommercedemo.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;
    private ModelMapper modelMapper;
    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo, ModelMapper modelMapper) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepo.findById(productDto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Product", "id", productDto.getCategoryId()));
        Product product = modelMapper.map(productDto, Product.class);
        product.setCategory(category);
        Product newProduct = productRepo.save(product);
        return modelMapper.map(newProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProductById(long id, ProductDto productDto) {
        Product product = productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product", "id", id));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        Category category = categoryRepo.findById(productDto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Product", "id", id));
        product.setCategory(category);
        Product newProduct = productRepo.save(product);
        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public ProductDto getProductById(Long id) {
        return modelMapper.map(productRepo.findById(id).orElseThrow(), ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(product -> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteProductById(long id) {
        Product product = productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product", "id", id));
        productRepo.delete(product);
    }

    @Override
    public List<ProductDto> findProductsByCategoryId(long id) {
        Category category = categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product", "id", id));
        List<Product> products = productRepo.findByCategoryId(id);
        return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }


}
