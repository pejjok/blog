package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.entities.CategoryEntity;
import com.pejjok.blog.repositories.CategoryRepository;
import com.pejjok.blog.services.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    @Transactional
    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
        String categoryName = categoryEntity.getName();
        if(categoryRepository.existsByNameIgnoreCase(categoryName)){
            throw new IllegalArgumentException("Category already exist with name "+ categoryName);
        }
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public void deleteCategory(UUID id) {
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        if (category.isPresent()){
            if (!category.get().getPosts().isEmpty()){
                throw new IllegalStateException("Category has post associated with it");
            }
            categoryRepository.deleteById(id);
        }
    }
}
