package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.PostStatus;
import com.pejjok.blog.domain.entities.CategoryEntity;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.TagEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.repositories.PostRepository;
import com.pejjok.blog.services.CategoryService;
import com.pejjok.blog.services.PostService;
import com.pejjok.blog.services.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    public PostServiceImpl(PostRepository postRepository, CategoryService categoryService, TagService tagService) {
        this.postRepository = postRepository;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostEntity> listOfPublishedPosts(UUID categoryId, UUID tagId) {
        if(categoryId != null && tagId != null){
            CategoryEntity category = categoryService.getCategoryById(categoryId);
            TagEntity tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if (categoryId != null){
            CategoryEntity category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        if (tagId != null){
            TagEntity tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);

    }

    @Override
    public List<PostEntity> listOfDraftedPosts(UserEntity user) {
        return postRepository.findAllByAuthorAndStatus(user,PostStatus.DRAFT);
    }
}
