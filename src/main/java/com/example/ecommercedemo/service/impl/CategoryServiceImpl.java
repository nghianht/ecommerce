package com.example.ecommercedemo.service.impl;

import com.example.ecommercedemo.dto.CategoryDto;
import com.example.ecommercedemo.dto.ProductDto;
import com.example.ecommercedemo.entity.Category;
import com.example.ecommercedemo.exception.ResourceNotFoundException;
import com.example.ecommercedemo.repo.CategoryRepo;
import com.example.ecommercedemo.service.CategoryService;
import com.example.ecommercedemo.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private ModelMapper modelMapper;
    private ProductService productService;
    private CategoryRepo categoryRepo;
    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper, ProductService productService, CategoryRepo categoryRepo) {
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.categoryRepo = categoryRepo;
    }
    @Override
    public CategoryDto createCate(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category newCategory = categoryRepo.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCateById(long id, CategoryDto categoryDto) {
        Category category = categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", "id", id));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category newCate = categoryRepo.save(category);
        return modelMapper.map(newCate, CategoryDto.class);
    }

    @Override
    public CategoryDto getCateById(long id) {
        Category category = categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", "id", id));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCate() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream().map(category -> modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteCateById(long id) {
        Category category = categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", "id", id));
        categoryRepo.delete(category);
    }
}
