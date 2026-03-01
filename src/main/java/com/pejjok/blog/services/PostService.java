package com.pejjok.blog.services;

import com.pejjok.blog.domain.dtos.CreatePostRequest;
import com.pejjok.blog.domain.dtos.UpdatePostRequest;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PostService {
    Page<PostEntity> listOfPublishedPosts(UUID categoryId, UUID tagId, Pageable pageable);
    Page<PostEntity> listOfDraftedPosts(UserEntity user, Pageable pageable);
    PostEntity createPost(UserEntity user, CreatePostRequest createPostRequestDto);
    PostEntity updatePost(UserEntity user, UUID postId, @Valid UpdatePostRequest updatePostRequest);
    PostEntity getPostById(UUID id);
    void deletePost(UUID id);
}
