package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.entities.CategoryEntity;
import com.pejjok.blog.repositories.CategoryRepository;
import com.pejjok.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> listOfCategories() {
        //Uses JOIN FETCH to prevent N+1 Problem when counting post
        return categoryRepository.findAllWithPosts();
    }
}
