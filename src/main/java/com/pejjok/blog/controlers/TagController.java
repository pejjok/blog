package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.CreateTagsRequest;
import com.pejjok.blog.domain.dtos.TagResponse;
import com.pejjok.blog.domain.entities.TagEntity;
import com.pejjok.blog.mappers.TagMapper;
import com.pejjok.blog.services.TagService;
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
    public ResponseEntity<List<TagResponse>> getTags(){
        List<TagResponse> listOfTags = tagService.listOfTags().stream()
                .map(tagMapper::toTagResponse)
                .toList();
        return ResponseEntity.ok(listOfTags);
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTags(@RequestBody CreateTagsRequest createTagsRequest){
        List<TagEntity> savedTags = tagService.createTags(createTagsRequest.getNames());
        List<TagResponse> listOfTags = savedTags.stream()
                .map(tagMapper::toTagResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(listOfTags);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id){
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
