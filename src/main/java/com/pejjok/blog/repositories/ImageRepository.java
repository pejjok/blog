package com.pejjok.blog.repositories;

import com.pejjok.blog.domain.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ImageRepository  extends JpaRepository<ImageEntity, UUID> {

    List<ImageEntity> findByPostNullAndCreatedAtBefore(LocalDateTime createdAt);
}
