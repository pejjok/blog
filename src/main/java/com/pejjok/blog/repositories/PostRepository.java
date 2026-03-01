package com.pejjok.blog.repositories;

import com.pejjok.blog.domain.PostStatus;
import com.pejjok.blog.domain.entities.CategoryEntity;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.TagEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    Page<PostEntity> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, CategoryEntity category, TagEntity tag, Pageable pageable);
    Page<PostEntity> findAllByStatusAndCategory(PostStatus status, CategoryEntity category, Pageable pageable);
    Page<PostEntity> findAllByStatusAndTagsContaining(PostStatus status, TagEntity tag, Pageable pageable);
    Page<PostEntity> findAllByStatus(PostStatus status, Pageable pageable);
    Page<PostEntity> findAllByAuthorAndStatus(UserEntity author,PostStatus status, Pageable pageable);
}
