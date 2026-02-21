package com.pejjok.blog.services;

import com.pejjok.blog.domain.entities.PostEntity;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostEntity> listOfPublishedPosts(UUID categoryId, UUID tagId);
}
