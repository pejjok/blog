package com.pejjok.blog.services;

import com.pejjok.blog.domain.entities.CategoryEntity;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryEntity> listOfCategories();
    CategoryEntity createCategory(CategoryEntity categoryEntity);
    void deleteCategory(UUID id);
}
