package com.likelion.likelionassignmentoauth.post.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record PostUpdateRequestDto(
        @NotBlank(message = "제목을 입력하세요")
        String title,
        @NotBlank(message = "null 이거나 공백이면 안 됩니다.")
        String content
){
}
