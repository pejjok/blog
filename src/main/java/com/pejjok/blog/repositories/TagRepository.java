package com.pejjok.blog.repositories;

import com.pejjok.blog.domain.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, UUID> {
    @Query("SELECT t FROM TagEntity t LEFT JOIN FETCH t.posts")
    List<TagEntity> findAllWithPosts();
    List<TagEntity> findByNameIn(Set<String> tagNames);
}
