package com.pejjok.blog.services;

import com.pejjok.blog.domain.entities.TagEntity;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<TagEntity> listOfTags();
    List<TagEntity> createTags(Set<String> tagNames);
}
