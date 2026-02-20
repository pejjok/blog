package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.TagResponse;
import com.pejjok.blog.mappers.TagMapper;
import com.pejjok.blog.services.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
