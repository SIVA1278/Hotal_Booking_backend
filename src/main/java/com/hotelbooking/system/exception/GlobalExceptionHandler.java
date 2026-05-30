package com.hotelbooking.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException e){
        return e.getMessage();
    }

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequest(BadRequestException e){
        return e.getMessage();
    }
}
