package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.repositories.UserRepository;
import com.pejjok.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getUserById(UUID userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User not found with id " + userId));
    }
}
