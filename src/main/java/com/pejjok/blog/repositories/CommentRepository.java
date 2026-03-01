package com.pejjok.blog.repositories;

import com.pejjok.blog.domain.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
}
