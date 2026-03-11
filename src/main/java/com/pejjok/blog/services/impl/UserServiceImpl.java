package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.UserRole;
import com.pejjok.blog.domain.dtos.ChangeRoleRequest;
import com.pejjok.blog.domain.entities.RoleEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.repositories.RoleReposiory;
import com.pejjok.blog.repositories.UserRepository;
import com.pejjok.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleReposiory roleReposiory;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleReposiory roleReposiory, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleReposiory = roleReposiory;
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
        if(user.getRole()==null){
            user.setRole(roleReposiory.findByName(UserRole.USER.getRole()));
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

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserEntity changeRole(UserEntity user,UUID id, ChangeRoleRequest changeRoleRequest) {
        if (!user.getRole().getName().equals(UserRole.ADMIN.getRole())){
            throw new AccessDeniedException("Access denied");
        }

        String role = changeRoleRequest.getRole();
        UserEntity userToAssign = userRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("User not found with id " + id)
        );
        RoleEntity roleEntity = roleReposiory.findByName(role);
        if (roleEntity == null){
            throw new EntityNotFoundException("Role not found with name " + role);
        }
        userToAssign.setRole(roleEntity);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
