package com.lujx3419.usersystem.common;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle custom business exceptions
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<String> handleBusinessException(BusinessException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ApiResponse.error(errorMsg);
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception ex) {
        ex.printStackTrace();
        return ApiResponse.error("Internal server error");
    }
}
