package com.pejjok.blog.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTagRequest {
    @Size(min = 2,max = 30,message = "Tag name must be between {min} and {max} characters")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s-]+$",flags = Pattern.Flag.UNICODE_CASE, message = "Tag name can only conrain letters, numbers and space")
    private String name;
}
