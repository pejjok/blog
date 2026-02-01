package com.pejjok.blog.services;

import com.pejjok.blog.domain.entities.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> listOfCategories();
    CategoryEntity createCategory(CategoryEntity categoryEntity);
}
