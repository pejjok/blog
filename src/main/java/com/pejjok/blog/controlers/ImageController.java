package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.ImageDto;
import com.pejjok.blog.domain.entities.ImageEntity;
import com.pejjok.blog.mappers.ImageMapper;
import com.pejjok.blog.services.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/images")
public class ImageController {

    private final ImageService imageService;
    private final ImageMapper imageMapper;


    public ImageController(ImageService imageService, ImageMapper imageMapper) {
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @PostMapping
    public ResponseEntity<ImageDto> uploadImage(@RequestParam("file") MultipartFile file){
        ImageEntity image = imageService.uploadImage(file);
        ImageDto imageDto = imageMapper.toDto(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageDto);
    }

    @GetMapping("/{id:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String id){
        return imageService.loadAsResource(id).map(image ->
                    ResponseEntity.ok()
                            .contentType(MediaTypeFactory
                                    .getMediaType(image)
                                    .orElse(MediaType.APPLICATION_OCTET_STREAM)
                            )
                            .header(HttpHeaders.CONTENT_DISPOSITION,"inline")
                            .body(image)
                )
                .orElse(ResponseEntity.notFound().build());
    }
}
