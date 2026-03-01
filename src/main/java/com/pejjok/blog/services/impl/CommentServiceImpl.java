package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.PostStatus;
import com.pejjok.blog.domain.dtos.CreateCommentRequest;
import com.pejjok.blog.domain.entities.CommentEntity;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.repositories.CommentRepository;
import com.pejjok.blog.services.CommentService;
import com.pejjok.blog.services.PostService;
import com.pejjok.blog.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;


    public CommentServiceImpl(CommentRepository commentRepository, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
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
        CommentEntity newComment = CommentEntity.builder()
                .content(createCommentRequest.getContent())
                .post(post)
                .author(user)
                .build();

        return commentRepository.save(newComment);
    }
}
