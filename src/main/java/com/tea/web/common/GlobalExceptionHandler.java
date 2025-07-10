package com.tea.web.common;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseMessageDto handleCustomException(CustomException ex) {
        return new ResponseMessageDto(ex.getErrorType().getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseMessageDto handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseMessageDto(errorMessage);
    }
}
