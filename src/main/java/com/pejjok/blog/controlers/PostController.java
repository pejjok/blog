package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.CreatePostRequest;
import com.pejjok.blog.domain.dtos.PostDto;
import com.pejjok.blog.domain.dtos.UpdatePostRequest;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.mappers.PostMapper;
import com.pejjok.blog.security.BlogUserDetails;
import com.pejjok.blog.services.PostService;
import com.pejjok.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Page<PostDto>> getAllPublishedPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId,
            @PageableDefault(page = 0, size = 20, sort = "createdAt" , direction = Sort.Direction.DESC) Pageable pageable){
        Page<PostEntity> postEntities = postService.listOfPublishedPosts(categoryId,tagId,pageable);
        Page<PostDto> postDtos = postEntities.map(postMapper::toDto);
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable UUID id){
        PostEntity postEntity = postService.getPostById(id);
        PostDto postDto = postMapper.toDto(postEntity);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/drafts")
    public ResponseEntity<Page<PostDto>> getAllDraftedPosts(
            @AuthenticationPrincipal BlogUserDetails userDetails,
            @PageableDefault(page = 0, size = 20, sort = "createdAt" , direction = Sort.Direction.DESC) Pageable pageable
            ){
        UserEntity loggedInUser = userDetails.getUser();
        Page<PostEntity> draftedPost = postService.listOfDraftedPosts(loggedInUser, pageable);
        Page<PostDto> postDtos = draftedPost.map(postMapper::toDto);
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @RequestBody @Valid CreatePostRequest createPostRequest,
            @AuthenticationPrincipal BlogUserDetails userDetails
    ){
        UserEntity loggedInUser = userDetails.getUser();
        PostEntity newPost = postService.createPost(loggedInUser, createPostRequest);
        PostDto newPostDto = postMapper.toDto(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPostDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @RequestBody @Valid UpdatePostRequest updatePostRequest,
            @AuthenticationPrincipal BlogUserDetails userDetails
    ){
        UserEntity loggedInUser = userDetails.getUser();
        PostEntity newPost = postService.updatePost(loggedInUser, id, updatePostRequest);
        PostDto newPostDto = postMapper.toDto(newPost);
        return ResponseEntity.ok(newPostDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
