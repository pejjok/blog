package com.pejjok.blog.mappers;

import com.pejjok.blog.domain.PostStatus;
import com.pejjok.blog.domain.dtos.CategoryDto;
import com.pejjok.blog.domain.entities.CategoryEntity;
import com.pejjok.blog.domain.entities.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    @Mapping(target = "postCount",source = "posts",qualifiedByName = "calculatePostCount")
    CategoryDto toDto(CategoryEntity categoryEntity);

    @Named("calculatePostCount")
    default Long calculatePostCount(List<PostEntity> posts){
        if (posts == null) {
            return 0l;
        }

        //Count only post that already published
        return posts.stream().filter(post->post.getStatus().equals(PostStatus.PUBLISHED)).count();
    }
}
