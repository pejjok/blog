package com.pejjok.blog.services;

import com.pejjok.blog.domain.entities.ImageEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {
    ImageEntity uploadImage(MultipartFile file);
    Optional<Resource> loadAsResource(String filename);
}
