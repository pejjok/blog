package com.pejjok.blog.mappers;

import com.pejjok.blog.domain.dtos.RegisterRequest;
import com.pejjok.blog.domain.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserEntity toEntity(RegisterRequest registerRequest);
}
