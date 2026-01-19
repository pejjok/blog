package com.pejjok.blog;

import com.pejjok.blog.domain.entities.CategoryEntity;
import com.pejjok.blog.domain.entities.TagEntity;
import com.pejjok.blog.domain.entities.UserEntity;

import java.util.UUID;

public class TestDataUtils {

    public static UserEntity createTestUserEntityA(){
        return UserEntity.builder()
                .name("userA")
                .email("emailA@gmail.com")
                .password("password")
                .build();
    }

    public static UserEntity createTestUserEntityB(){
        return UserEntity.builder()
                .name("userB")
                .email("emailB@gmail.com")
                .password("password")
                .build();
    }

    public static UserEntity createTestUserEntityC(){
        return UserEntity.builder()
                .name("userC")
                .email("emailC@gmail.com")
                .password("password")
                .build();
    }


    public static CategoryEntity createTestCategoryEntityA(){
        return CategoryEntity.builder()
                .name("CategoryA")
                .build();
    }
    public static CategoryEntity createTestCategoryEntityB(){
        return CategoryEntity.builder()
                .name("CategoryB")
                .build();
    }
    public static CategoryEntity createTestCategoryEntityC(){
        return CategoryEntity.builder()
                .name("CategoryC")
                .build();
    }


    public static TagEntity createTestTagEntityA(){
        return TagEntity.builder()
                .name("TagA")
                .build();
    }
    public static TagEntity createTestTagEntityB(){
        return TagEntity.builder()
                .name("TagB")
                .build();
    }
    public static TagEntity createTestTagEntityC(){
        return TagEntity.builder()
                .name("TagC")
                .build();
    }
}
