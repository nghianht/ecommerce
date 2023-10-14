package com.example.ecommercedemo.controller;

import com.example.ecommercedemo.dto.CategoryDto;
import com.example.ecommercedemo.dto.ProductDto;
import com.example.ecommercedemo.service.CategoryService;
import com.example.ecommercedemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private ProductService productService;
    private CategoryService categoryService;
    @Autowired
    public CategoryController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCate(@RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.createCate(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCate(){
        List<CategoryDto> categoryDtos = categoryService.getAllCate();
        return  new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCateById(@PathVariable("id")long id){
        CategoryDto categoryDto = categoryService.getCateById(id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCateById(@PathVariable("{id}")long id, @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.updateCateById(id, categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public String deleteCate(@PathVariable("id")long id){
        categoryService.deleteCateById(id);
        return "Xoá thành cmn công" + id;
    }
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getProductsByCateId(@PathVariable("id") long id){
        List<ProductDto> products= productService.findProductsByCategoryId(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
