package com.pejjok.blog.config;

import com.pejjok.blog.domain.entities.ImageEntity;
import com.pejjok.blog.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@Slf4j
public class ImageCleanupScheduler {

    private final ImageService imageService;

    public ImageCleanupScheduler(ImageService imageService) {
        this.imageService = imageService;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void imageCleanup() {
        List<ImageEntity> images = imageService.findAllNotUsedImages();
        for (var image : images){
            imageService.delete(image.getId());
        }
    }
}
