package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.CommentDto;
import com.pejjok.blog.domain.dtos.CreateCommentRequest;
import com.pejjok.blog.domain.dtos.UpdateCommentRequest;
import com.pejjok.blog.domain.entities.CommentEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.mappers.CommentMapper;
import com.pejjok.blog.security.BlogUserDetails;
import com.pejjok.blog.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @AuthenticationPrincipal BlogUserDetails userDetails,
            @PathVariable UUID postId,
            @RequestBody @Valid CreateCommentRequest createCommentRequest
    ){
        UserEntity loggedInUser = userDetails.getUser();
        CommentEntity newComment = commentService.createComment(loggedInUser, postId, createCommentRequest);
        CommentDto newCommentDto = commentMapper.toDto(newComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCommentDto);
    }

    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<Page<CommentDto>> getComments(
            @PathVariable UUID postId,
            @PageableDefault(page = 0, size = 10, sort = "createdAt" , direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<CommentEntity> comments = commentService.getComments(postId, pageable);
        Page<CommentDto> commentsDto = comments.map(commentMapper::toDto);
        return ResponseEntity.ok(commentsDto);
    }

    @PutMapping("comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @AuthenticationPrincipal BlogUserDetails userDetails,
            @PathVariable UUID id,
            @RequestBody @Valid UpdateCommentRequest updateCommentRequest
    ){
        UserEntity loggedInUser = userDetails.getUser();
        CommentEntity updatedComment = commentService.updateComment(loggedInUser, id, updateCommentRequest);
        CommentDto updatedCommentDto = commentMapper.toDto(updatedComment);
        return ResponseEntity.ok(updatedCommentDto);
    }
}
