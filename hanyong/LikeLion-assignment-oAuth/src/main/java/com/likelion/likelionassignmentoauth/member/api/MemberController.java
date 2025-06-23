package com.likelion.likelionassignmentoauth.member.api;

import com.likelion.likelionassignmentoauth.common.error.SuccessCode;
import com.likelion.likelionassignmentoauth.common.template.ApiResTemplate;
import com.likelion.likelionassignmentoauth.member.api.dto.request.MemberJoinRequestDto;
import com.likelion.likelionassignmentoauth.member.api.dto.request.MemberLoginRequestDto;
import com.likelion.likelionassignmentoauth.member.api.dto.response.MemberInfoResponseDto;
import com.likelion.likelionassignmentoauth.member.application.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/join")
    public ApiResTemplate<String> join(@RequestBody @Valid MemberJoinRequestDto memberJoinReqDto) {
        memberService.join(memberJoinReqDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.MEMBER_JOIN_SUCCESS);
    }

    // 로그인
    @PostMapping("/login")
    public ApiResTemplate<MemberInfoResponseDto> login(@RequestBody @Valid MemberLoginRequestDto memberLoginReqDto) {
        MemberInfoResponseDto memberInfoResDto = memberService.login(memberLoginReqDto);
        return ApiResTemplate.successResponse(SuccessCode.MEMBER_LOGIN_SUCCESS, memberInfoResDto);
    }
}