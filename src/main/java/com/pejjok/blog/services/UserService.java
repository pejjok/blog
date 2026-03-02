package com.pejjok.blog.services;

import com.pejjok.blog.domain.dtos.ChangeRoleRequest;
import com.pejjok.blog.domain.entities.UserEntity;
import org.apache.catalina.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserEntity getUserById(UUID userId);
    UserEntity createUser(UserEntity user);
    UserEntity getUserByEmail(String email);
    boolean existsByEmail(String email);
    List<UserEntity> getAllUsers();
    UserEntity changeRole(UserEntity user, UUID id, ChangeRoleRequest changeRoleRequest);
}
