package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.PostStatus;
import com.pejjok.blog.domain.UserRole;
import com.pejjok.blog.domain.dtos.CreateCommentRequest;
import com.pejjok.blog.domain.dtos.UpdateCommentRequest;
import com.pejjok.blog.domain.entities.CommentEntity;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.mappers.CommentMapper;
import com.pejjok.blog.repositories.CommentRepository;
import com.pejjok.blog.services.CommentService;
import com.pejjok.blog.services.PostService;
import com.pejjok.blog.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostService postService;
    private final UserService userService;


    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public CommentEntity createComment(UserEntity user, UUID postId, CreateCommentRequest createCommentRequest) {
        PostEntity post = postService.getPostById(postId);
        if (post.getStatus().equals(PostStatus.DRAFT)){
            throw new IllegalStateException("You can't comment on a draft post");
        }
        CommentEntity newComment = commentMapper.toEntity(user,post,createCommentRequest);

        return commentRepository.save(newComment);
    }

    @Override
    public Page<CommentEntity> getComments(UUID postId, Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public CommentEntity updateComment(UserEntity user, UUID id, UpdateCommentRequest updateCommentRequest) {
        CommentEntity existingComment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Comment not found with id " + id)
        );

        if(!existingComment.getAuthor().getId().equals(user.getId()) ){
            throw new AccessDeniedException("You are not allowed to update this post");
        }

        existingComment.setContent(updateCommentRequest.getContent());

        return commentRepository.save(existingComment);
    }
}
