package com.pejjok.blog.services;

import com.pejjok.blog.domain.dtos.CreatePostRequest;
import com.pejjok.blog.domain.dtos.UpdatePostRequest;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostEntity> listOfPublishedPosts(UUID categoryId, UUID tagId);
    List<PostEntity> listOfDraftedPosts(UserEntity user);
    PostEntity createPost(UserEntity user, CreatePostRequest createPostRequestDto);
    PostEntity updatePost(UUID postId, @Valid UpdatePostRequest updatePostRequest);
}
