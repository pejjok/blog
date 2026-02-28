package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.repositories.UserRepository;
import com.pejjok.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity getUserById(UUID userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User not found with id " + userId));
    }

    @Override
    @Transactional
    public UserEntity createUser(UserEntity user) {
        String email = user.getEmail();
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("User already exist with email " + email);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()->new EntityNotFoundException("User not found with email " + email));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
