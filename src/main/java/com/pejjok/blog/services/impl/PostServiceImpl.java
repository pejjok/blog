package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.dtos.CreatePostRequest;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
        return postRepository.findAllByAuthorAndStatus(user,PostStatus.DRAFT);
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
}
