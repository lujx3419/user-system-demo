package com.lujx3419.usersystem.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<String> handleBusinessException(BusinessException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleOtherException(Exception ex) {
        return ApiResponse.error("Server error: " + ex.getMessage());
    }
}
