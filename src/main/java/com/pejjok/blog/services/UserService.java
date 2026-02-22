package com.pejjok.blog.services;

import com.pejjok.blog.domain.entities.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity getUserById(UUID userId);
}
