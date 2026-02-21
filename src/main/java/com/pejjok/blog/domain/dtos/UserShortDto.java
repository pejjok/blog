package com.pejjok.blog.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// For PostDto
public class UserShortDto {
    private UUID id;
    private String name;
}
