package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.UserRole;
import com.pejjok.blog.domain.dtos.CreatePostRequest;
import com.pejjok.blog.domain.PostStatus;
import com.pejjok.blog.domain.dtos.UpdatePostRequest;
import com.pejjok.blog.domain.entities.CategoryEntity;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.TagEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.repositories.PostRepository;
import com.pejjok.blog.services.CategoryService;
import com.pejjok.blog.services.PostService;
import com.pejjok.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    public static final double WORD_PER_MINUTE = 200.0;
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
        if(user.getRole().getName().equals(UserRole.ADMIN.getRole())){ // Admin can see all drafts
            return postRepository.findAllByStatus(PostStatus.DRAFT);
        }
        return postRepository.findAllByAuthorAndStatus(user,PostStatus.DRAFT); // If user editor than only his drafts can be seen
    }

    @Override
    @Transactional
    public PostEntity createPost(UserEntity user, CreatePostRequest createPostRequestDto) {
        PostEntity newPost = new PostEntity();
        newPost.setTitle(createPostRequestDto.getTitle());
        newPost.setContent(createPostRequestDto.getContent());
        newPost.setAuthor(user);
        newPost.setStatus(createPostRequestDto.getStatus());
        newPost.setReadingTime(calculateReadingTime(createPostRequestDto.getContent()));

        CategoryEntity category = categoryService.getCategoryById(createPostRequestDto.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagsDto = createPostRequestDto.getTagIds();
        Set<TagEntity> tags = new HashSet<>(tagService.getTagByIds(tagsDto));
        newPost.setTags(tags);

        return postRepository.save(newPost);
    }

    private Integer calculateReadingTime(String content) {
        if(content == null || content.isEmpty()){
            return 0;
        }
        int wordCount = content.split("\\s+").length;
        return (int) Math.ceil(wordCount / WORD_PER_MINUTE);

    }

    @Override
    @Transactional
    public PostEntity updatePost(UserEntity user, UUID postId, UpdatePostRequest updatePostRequest) {
        PostEntity existingPost = postRepository.findById(postId)
                .orElseThrow(()->new EntityNotFoundException("Post not found with id " + postId));
        if(!existingPost.getAuthor().getId().equals(user.getId()) && !user.getRole().getName().equals(UserRole.ADMIN.getRole())){
            throw new AccessDeniedException("You are not allowed to update this post");
        }
        existingPost.setTitle(updatePostRequest.getTitle());
        existingPost.setContent(updatePostRequest.getContent());
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(updatePostRequest.getContent()));

        UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();
        if(!existingPost.getCategory().getId().equals(updatePostRequestCategoryId)){
            CategoryEntity category = categoryService.getCategoryById(updatePostRequestCategoryId);
            existingPost.setCategory(category);
        }

        Set<UUID> existingTagIds = existingPost.getTags().stream().map(TagEntity::getId).collect(Collectors.toSet());
        Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();
        if(!existingTagIds.equals(updatePostRequestTagIds)){
            Set<TagEntity> tags = new HashSet<>(tagService.getTagByIds(updatePostRequestTagIds));
            existingPost.setTags(tags);
        }

        return postRepository.save(existingPost);
    }

    @Override
    public PostEntity getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Post not found with id " + id));
    }

    @Override
    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }
}
