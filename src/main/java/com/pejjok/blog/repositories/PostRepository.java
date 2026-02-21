package com.pejjok.blog.repositories;

import com.pejjok.blog.domain.PostStatus;
import com.pejjok.blog.domain.entities.CategoryEntity;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    List<PostEntity> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, CategoryEntity category, TagEntity tag);
    List<PostEntity> findAllByStatusAndCategory(PostStatus status, CategoryEntity category);
    List<PostEntity> findAllByStatusAndTagsContaining(PostStatus status, TagEntity tag);
    List<PostEntity> findAllByStatus(PostStatus status);

}
