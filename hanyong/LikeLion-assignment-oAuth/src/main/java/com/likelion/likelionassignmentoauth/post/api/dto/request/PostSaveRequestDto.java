package com.likelion.likelionassignmentoauth.post.api.dto.request;

import jakarta.validation.constraints.*;

public record PostSaveRequestDto(
        Long memberId,
        @NotBlank(message = "제목을 입력하세요")
        String title,
        @NotBlank(message = "null 이거나 공백이면 안 됩니다.")
        String content
){
}
