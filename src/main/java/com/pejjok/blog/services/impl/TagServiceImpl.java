package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.entities.TagEntity;
import com.pejjok.blog.repositories.TagRepository;
import com.pejjok.blog.services.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagEntity> listOfTags() {
        return tagRepository.findAllWithPosts();
    }
}
