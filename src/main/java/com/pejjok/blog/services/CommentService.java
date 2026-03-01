package com.pejjok.blog.services;

import com.pejjok.blog.domain.dtos.CreateCommentRequest;
import com.pejjok.blog.domain.dtos.UpdateCommentRequest;
import com.pejjok.blog.domain.entities.CommentEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CommentService {
    CommentEntity createComment(UserEntity user, UUID postId, CreateCommentRequest createCommentRequest);
    Page<CommentEntity> getComments(UUID postId, Pageable pageable);
    CommentEntity updateComment(UserEntity user, UUID id, UpdateCommentRequest updateCommentRequest);
    void deleteComment(UserEntity user, UUID id);
}
