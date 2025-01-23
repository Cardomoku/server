package com.jjweidon.cardomoku.global.exception;

import com.jjweidon.cardomoku.domain.user.exception.UserNotFoundException;
import com.jjweidon.cardomoku.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    // UserNotFoundException 처리
    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return ApiResponse.failure(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // IllegalStateException 처리
    @ExceptionHandler(IllegalStateException.class)
    public ApiResponse<Object> handleIllegalStateException(IllegalStateException ex) {
        return ApiResponse.failure(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 기타 모든 예외 처리 (500 서버 에러)
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleException(Exception ex) {
        return ApiResponse.failure("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}