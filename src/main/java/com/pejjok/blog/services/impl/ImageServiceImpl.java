package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.entities.ImageEntity;
import com.pejjok.blog.repositories.ImageRepository;
import com.pejjok.blog.services.ImageService;
import com.pejjok.blog.services.StorageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    public List<ImageEntity> getImageByIds(List<UUID> ids) {
        List<ImageEntity> images = imageRepository.findAllById(ids);
        if (images.size() != ids.size()){
            throw new EntityNotFoundException("Not all specified images are existing");
        }
        return images;
    }

    @Override
    public List<ImageEntity> findAllNotUsedImages() {
        return imageRepository.findByPostNullAndCreatedAtBefore(LocalDateTime.now().minusDays(7)); // Images that not used in post and was created more than 7 days ago
    }

    @Override
    public void delete(UUID id) {
        Optional<ImageEntity> image = imageRepository.findById(id);
        if (image.isPresent()){
            storageService.delete(image.get().getFilename());
            imageRepository.delete(image.get());
        }
    }
}
