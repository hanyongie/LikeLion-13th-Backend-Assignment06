package com.likelion.likelionassignmentoauth.post.api;

import com.likelion.likelionassignmentoauth.common.error.SuccessCode;
import com.likelion.likelionassignmentoauth.common.template.ApiResTemplate;
import com.likelion.likelionassignmentoauth.post.api.dto.request.PostSaveRequestDto;
import com.likelion.likelionassignmentoauth.post.api.dto.request.PostUpdateRequestDto;
import com.likelion.likelionassignmentoauth.post.api.dto.response.PostListResponseDto;
import com.likelion.likelionassignmentoauth.post.application.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    //게시물 저장
    @PostMapping("/save")
    public ApiResTemplate<String> postSave(@RequestBody @Valid PostSaveRequestDto postSaveRequestDto, Principal principal) {
        postService.postSave(postSaveRequestDto, principal);
        return ApiResTemplate.successWithNoContent(SuccessCode.POST_SAVE_SUCCESS);
    }

    //전체 게시물 조회
    @GetMapping("/all")
    public ApiResTemplate<PostListResponseDto>menuFindAll(){
        PostListResponseDto postListResponseDto = postService.postFindAll();
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, postListResponseDto);
    }
    //멤버 id를 기준으로 해당 게시물 조회
    //principal 객체로 토큰에서 사용자 판별 후 memberId를 보내지 않아도 자동으로 조회
    @GetMapping("/members")
    public ApiResTemplate<PostListResponseDto> myMenuFindAll(Principal principal){
        PostListResponseDto postListResponseDto = postService.postFindById(principal);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, postListResponseDto);
    }
    //게시물 id로 수정
    @PatchMapping("/{postId}")
    public ApiResTemplate<String>menuUpdate(@PathVariable("postId")Long postId,
                                            @RequestBody PostUpdateRequestDto postUpdateRequestDto){
        postService.postUpdate(postId, postUpdateRequestDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.POST_UPDATE_SUCCESS);
    }
    //게시물 id로 삭제
    @DeleteMapping("/{postId}")
    public ApiResTemplate<String> postDelete(@PathVariable("postId")Long postId){
        postService.postDelete(postId);
        return ApiResTemplate.successWithNoContent(SuccessCode.POST_DELETE_SUCCESS);
    }

}
