package com.pejjok.blog.services;

import com.pejjok.blog.domain.entities.TagEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
    List<TagEntity> listOfTags();
    List<TagEntity> createTags(Set<String> tagNames);
    void deleteTag(UUID id);
    TagEntity getTagById(UUID id);
    List<TagEntity> getTagByIds(Set<UUID> ids);
}
