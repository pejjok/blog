package com.pejjok.blog.mappers;

import com.pejjok.blog.domain.dtos.ImageDto;
import com.pejjok.blog.domain.entities.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {

    ImageDto toDto(ImageEntity imageEntity);
}
