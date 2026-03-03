package com.pejjok.blog.mappers;

import com.pejjok.blog.domain.dtos.PostDto;
import com.pejjok.blog.domain.entities.PostEntity;
import jakarta.persistence.Column;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author",source = "author")
    @Mapping(target = "category",source = "category")
    @Mapping(target = "tags",source = "tags")
    @Mapping(target = "status",source = "status")
    PostDto toDto(PostEntity post);

    @Mapping(target = "author",source = "post.author")
    @Mapping(target = "category",source = "post.category")
    @Mapping(target = "tags",source = "post.tags")
    @Mapping(target = "status",source = "post.status")
    @Mapping(target = "content",expression = "java(shortContent(post.getContent(), maxCharacters))")
    PostDto toDtoWithShortContent(PostEntity post, int maxCharacters);

    default String shortContent(String content, int maxCharacters){
        if (content.length() <= maxCharacters){
            return content;
        }
        return content.substring(0, maxCharacters);
    }
}
