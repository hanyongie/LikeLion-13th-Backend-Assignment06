package com.likelion.likelionassignmentoauth.member.api.dto.response;

import com.likelion.likelionassignmentoauth.member.domain.Member;
import lombok.Builder;

@Builder
public record MemberInfoResponseDto(
        String email,
        String name,
        String token
) {
    public static MemberInfoResponseDto of(Member member, String token) {
        return MemberInfoResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .token(token)
                .build();
    }
}
