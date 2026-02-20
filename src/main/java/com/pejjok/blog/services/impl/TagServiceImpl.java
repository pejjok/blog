package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.entities.TagEntity;
import com.pejjok.blog.repositories.TagRepository;
import com.pejjok.blog.services.TagService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public List<TagEntity> createTags(Set<String> tagNames) {
        List<TagEntity> existingTags = tagRepository.findByNameIn(tagNames);

        Set<String> existingTagsName = existingTags.stream()
                .map(TagEntity::getName)
                .collect(Collectors.toSet());

        List<TagEntity> newTags = tagNames.stream()
                .filter(t -> !existingTagsName.contains(t))
                .map(name-> TagEntity.builder().name(name).build())
                .toList();

        List<TagEntity> savedTags = new ArrayList<>();
        if (!newTags.isEmpty()){
            savedTags = tagRepository.saveAll(newTags);
        }
        savedTags.addAll(existingTags);

        return savedTags;
    }
}
