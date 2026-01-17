package com.pejjok.blog;

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
}
