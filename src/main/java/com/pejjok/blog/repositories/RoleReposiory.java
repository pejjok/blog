package com.pejjok.blog.repositories;

import com.pejjok.blog.domain.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleReposiory extends JpaRepository<RoleEntity, Integer> {
    boolean existsByName(String name);
    RoleEntity findByName(String name);
}
