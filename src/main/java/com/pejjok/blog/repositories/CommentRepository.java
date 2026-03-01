package com.pejjok.blog.repositories;

import com.pejjok.blog.domain.entities.CommentEntity;
import com.pejjok.blog.domain.entities.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);
}
