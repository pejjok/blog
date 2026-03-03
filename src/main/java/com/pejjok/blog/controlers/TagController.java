package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.CreateTagRequest;
import com.pejjok.blog.domain.dtos.TagDto;
import com.pejjok.blog.domain.entities.CategoryEntity;
import com.pejjok.blog.domain.entities.TagEntity;
import com.pejjok.blog.mappers.TagMapper;
import com.pejjok.blog.services.TagService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/tags")
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    public TagController(TagService tagService, TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getTags(){
        List<TagDto> listOfTags = tagService.listOfTags().stream()
                .map(tagMapper::toDto)
                .toList();
        return ResponseEntity.ok(listOfTags);
    }

    @PostMapping
    public ResponseEntity<TagDto> createTags(@RequestBody @Valid CreateTagRequest createTagRequest){
        TagEntity tagToCreate = tagMapper.toEntity(createTagRequest);
        TagEntity savedTag = tagService.createTag(tagToCreate);
        return new ResponseEntity<>(tagMapper.toDto(savedTag), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id){
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
