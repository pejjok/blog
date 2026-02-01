package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.CategoryDto;
import com.pejjok.blog.domain.dtos.CreateCategoryRequest;
import com.pejjok.blog.domain.entities.CategoryEntity;
import com.pejjok.blog.mappers.CategoryMapper;
import com.pejjok.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CreateCategoryRequest categoryRequest){
        CategoryEntity categoryToCreate = categoryMapper.toEntity(categoryRequest);
        CategoryEntity savedCategory = categoryService.createCategory(categoryToCreate);
        return new ResponseEntity<>(categoryMapper.toDto(savedCategory), HttpStatus.CREATED);
    }
}
