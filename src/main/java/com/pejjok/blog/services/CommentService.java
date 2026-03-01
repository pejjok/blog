package com.pejjok.blog.services;

import com.pejjok.blog.domain.dtos.CreateCommentRequest;
import com.pejjok.blog.domain.entities.CommentEntity;
import com.pejjok.blog.domain.entities.UserEntity;

import java.util.UUID;

public interface CommentService {
    CommentEntity createComment(UserEntity user, UUID postId, CreateCommentRequest createCommentRequest);
}
