package com.pejjok.blog.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagResponse {
    private UUID id;
    private String name;
    private Long postCount;
}
