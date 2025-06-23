package com.likelion.likelionassignmentoauth.post.api.dto.response;

import com.likelion.likelionassignmentoauth.post.domain.Post;
import lombok.Builder;

@Builder
public record PostInfoResponseDto(
        String title,
        String content
) {
    public static PostInfoResponseDto from(Post post){
        return PostInfoResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
