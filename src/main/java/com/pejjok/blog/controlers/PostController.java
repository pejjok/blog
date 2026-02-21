package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.PostDto;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.mappers.PostMapper;
import com.pejjok.blog.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }


    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId){
        List<PostEntity> postEntities = postService.listOfPublishedPosts(categoryId,tagId);
        List<PostDto> postDtos = postEntities.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }
}
