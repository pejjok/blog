package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.CreatePostRequest;
import com.pejjok.blog.domain.dtos.PostDto;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.mappers.PostMapper;
import com.pejjok.blog.services.PostService;
import com.pejjok.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final PostMapper postMapper;

    public PostController(PostService postService, UserService userService, PostMapper postMapper) {
        this.postService = postService;
        this.userService = userService;
        this.postMapper = postMapper;
    }


    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPublishedPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId){
        List<PostEntity> postEntities = postService.listOfPublishedPosts(categoryId,tagId);
        List<PostDto> postDtos = postEntities.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostDto>> getAllDraftedPosts(
            @RequestAttribute UUID userId
    ){
        UserEntity loggedInUser = userService.getUserById(userId);
        List<PostEntity> draftedPost = postService.listOfDraftedPosts(loggedInUser);
        List<PostDto> postDtos = draftedPost.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @RequestBody @Valid CreatePostRequest createPostRequest,
            @RequestAttribute UUID userId
    ){
        UserEntity loggedInUser = userService.getUserById(userId);
        PostEntity newPost = postService.createPost(loggedInUser, createPostRequest);
        PostDto newPostDto = postMapper.toDto(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPostDto);
    }
}
