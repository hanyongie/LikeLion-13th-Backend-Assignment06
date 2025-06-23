package com.likelion.likelionassignmentoauth.common.template;

import com.likelion.likelionassignmentoauth.common.error.ErrorCode;
import com.likelion.likelionassignmentoauth.common.error.SuccessCode;
import lombok.*;

@Getter //
@AllArgsConstructor(access = AccessLevel.PRIVATE) //모든 필드 값을 메개변수로 받는 생성자 자동 생성
@RequiredArgsConstructor(access = AccessLevel.PRIVATE) //final 붙은 필드만 매개변수로 받는 생성자 자동 생성
@Builder //빌더 패턴
public class ApiResTemplate<T> {

    private final int code; //응답 코드
    private final String message; //응답 메세지
    private T data; //응답 데이터

    //데이터 없는 성공 응답
    public static ApiResTemplate successWithNoContent(SuccessCode successCode){
        return new ApiResTemplate<>(successCode.getHttpStatusCode(), successCode.getMessage());
    }

    //데이터 포함한 성공 응답
    public static <T> ApiResTemplate<T> successResponse(SuccessCode successCode, T data){
        return new ApiResTemplate<>(successCode.getHttpStatusCode(),successCode.getMessage(),data);
    }
    //에러 응답
    public static ApiResTemplate errorResponse(ErrorCode errorCode, String customMessage){
        return new ApiResTemplate<>(errorCode.getHttpStatusCode(), customMessage);
    }
}
