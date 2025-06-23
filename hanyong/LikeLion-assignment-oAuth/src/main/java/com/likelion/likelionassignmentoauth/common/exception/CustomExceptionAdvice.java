package com.likelion.likelionassignmentoauth.common.exception;

import com.likelion.likelionassignmentoauth.common.error.ErrorCode;
import com.likelion.likelionassignmentoauth.common.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j //로깅을 위한 Logger 생성
@RestControllerAdvice
@Component
@RequiredArgsConstructor
public class CustomExceptionAdvice {
    /**
     * 500 Internal Server Error
     * 원인 모를 이유의 예외 발생 시
     * 모든 종류의 Exception 처리
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//Http 500상태 코드 반환 애노테이션
    @ExceptionHandler(Exception.class) //모든 종류의 Exception 처리
    public ApiResTemplate handleServerException(final Exception e){
        log.error("Internal Server Error: {}", e.getMessage(), e); //로그 출력
        return ApiResTemplate.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    /**
     * custom error
     * 내부 커스텀 에러
     * BusinessException 처리
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResTemplate> handleCustomException(BusinessException e){
        log.error("CustomException: {}", e.getMessage(), e);//로그 출력

        ApiResTemplate<?> body = ApiResTemplate.errorResponse(e.getErrorCode(), e.getMessage());
        //에러 코드에 정의된 HTTP 상태 코드로 응답
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(body);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResTemplate<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        // 에러 메시지 생성
        // 에러 메세지를 저장할 errorMap 생성
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // 응답 생성
        return ApiResTemplate.errorResponse(ErrorCode.VALIDATION_EXCEPTION, convertMapToString(errorMap));
    }

    private String convertMapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append(", ");
        }
        sb.deleteCharAt(sb.length() - 1); // 마지막 띄어쓰기 제거
        sb.deleteCharAt(sb.length() - 1); // 마지막 쉼표 제거
        return sb.toString();
    }
}
