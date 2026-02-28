package com.pejjok.blog.services;

import com.pejjok.blog.domain.entities.UserEntity;
import org.apache.catalina.User;

import java.util.UUID;

public interface UserService {
    UserEntity getUserById(UUID userId);
    UserEntity createUser(UserEntity user);
}
