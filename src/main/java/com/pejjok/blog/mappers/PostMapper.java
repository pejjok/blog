package com.pejjok.blog.mappers;

import com.pejjok.blog.domain.dtos.PostDto;
import com.pejjok.blog.domain.entities.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author",source = "author")
    @Mapping(target = "category",source = "category")
    @Mapping(target = "tags",source = "tags")
    @Mapping(target = "status",source = "status")
    PostDto toDto(PostEntity post);
}
