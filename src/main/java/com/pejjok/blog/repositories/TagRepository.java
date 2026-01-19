package com.pejjok.blog.repositories;

import com.pejjok.blog.domain.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, UUID> {
}
