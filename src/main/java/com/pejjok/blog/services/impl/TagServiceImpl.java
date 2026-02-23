package com.pejjok.blog.services.impl;

import com.pejjok.blog.domain.entities.TagEntity;
import com.pejjok.blog.repositories.TagRepository;
import com.pejjok.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Override
    @Transactional
    public void deleteTag(UUID id) {
        Optional<TagEntity> tag = tagRepository.findById(id);
        if (tag.isPresent()){
            if (!tag.get().getPosts().isEmpty()) {
                throw new IllegalStateException("Tag has post associated with it");
            }
            tagRepository.deleteById(id);
        }
    }

    @Override
    public TagEntity getTagById(UUID id) {
        return tagRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Tag not found with id "+ id));
    }

    @Override
    public List<TagEntity> getTagByIds(Set<UUID> ids) {
        List<TagEntity> tags = tagRepository.findAllById(ids);
        if (tags.size() != ids.size()){
            throw new EntityNotFoundException("Not all specified tags are existing");
        }
        return tags;
    }
}
