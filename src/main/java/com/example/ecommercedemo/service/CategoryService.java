package com.example.ecommercedemo.service;

import com.example.ecommercedemo.dto.CategoryDto;
import com.example.ecommercedemo.dto.ProductDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCate(CategoryDto categoryDto);
    CategoryDto updateCateById(long id, CategoryDto categoryDto);
    CategoryDto getCateById(long id);
    List<CategoryDto> getAllCate();
    void deleteCateById(long id);
}
