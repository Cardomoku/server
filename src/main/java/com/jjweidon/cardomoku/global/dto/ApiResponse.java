package com.jjweidon.cardomoku.global.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> extends ResponseEntity<ApiResponse.Body<T>> {

    private ApiResponse(Body<T> body, HttpStatus status) {
        super(body, status);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        Body<T> body = new Body<>(true, message, data);
        return new ApiResponse<>(body, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> failure(String message, HttpStatus status) {
        Body<T> body = new Body<>(false, message, null);
        return new ApiResponse<>(body, status);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return failure(message, HttpStatus.BAD_REQUEST);
    }

    public record Body<T>(boolean success, String message, T data) {
    }
}