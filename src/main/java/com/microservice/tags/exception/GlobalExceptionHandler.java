package com.microservice.tags.exception;


import com.microservice.tags.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now().toString(), HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleException(BadRequestException exception) {

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now().toString(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
