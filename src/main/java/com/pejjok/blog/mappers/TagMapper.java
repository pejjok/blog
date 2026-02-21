package com.pejjok.blog.mappers;

import com.pejjok.blog.domain.PostStatus;
import com.pejjok.blog.domain.dtos.TagDto;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagDto toTagDto(TagEntity tagEntity);

    @Named("calculatePostCount")
    default Long calculatePostCount(List<PostEntity> posts){
        if (posts == null) {
            return 0l;
        }

        //Count only post that already published
        return posts.stream().filter(post->post.getStatus().equals(PostStatus.PUBLISHED)).count();
    }
}
