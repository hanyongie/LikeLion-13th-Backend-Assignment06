package com.likelion.likelionassignmentoauth.common.exception;

import com.likelion.likelionassignmentoauth.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode; //에러 코드
    private final String customMessage; //사용자 정의 예외 메세지
    //생성자
    public BusinessException(ErrorCode errorCode, String customMessage) {
        super(customMessage); //RuntimeException의 생성자에 메시지 전달
        this.customMessage = customMessage;
        this.errorCode = errorCode;
}
}
