package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.CategoryDto;
import com.pejjok.blog.mappers.CategoryMapper;
import com.pejjok.blog.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    public final CategoryService categoryService;
    public final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper  = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        List<CategoryDto> categoriesDto = categoryService.listOfCategories().stream().map(categoryMapper::toDto).toList();

        return ResponseEntity.ok(categoriesDto);
    }

}
