package com.pejjok.blog.mappers;

import com.pejjok.blog.domain.dtos.CommentDto;
import com.pejjok.blog.domain.dtos.CreateCommentRequest;
import com.pejjok.blog.domain.entities.CommentEntity;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", source = "createCommentRequest.content")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "author", source = "user")
    CommentEntity toEntity(UserEntity user, PostEntity post, CreateCommentRequest createCommentRequest);
    CommentDto toDto(CommentEntity commentEntity);
}
