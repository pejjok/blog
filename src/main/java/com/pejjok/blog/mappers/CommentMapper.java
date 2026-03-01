package com.pejjok.blog.mappers;

import com.pejjok.blog.domain.dtos.CommentDto;
import com.pejjok.blog.domain.entities.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    CommentDto toDto(CommentEntity commentEntity);
}
