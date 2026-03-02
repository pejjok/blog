package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.entities.ImageEntity;
import com.pejjok.blog.repositories.ImageRepository;
import com.pejjok.blog.services.ImageService;
import com.pejjok.blog.services.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final StorageService storageService;

    public ImageServiceImpl(ImageRepository imageRepository, StorageService storageService) {
        this.imageRepository = imageRepository;
        this.storageService = storageService;
    }

    @Override
    public ImageEntity uploadImage(MultipartFile file) {
        String fileId = UUID.randomUUID().toString();
        String storedFilename = storageService.store(file, fileId);

        ImageEntity imageEntity = ImageEntity.builder()
                .filename(storedFilename)
                .build();

        return imageRepository.save(imageEntity);
    }

    @Override
    public Optional<Resource> loadAsResource(String filename) {
        return storageService.loadAsResource(filename);
    }
}
