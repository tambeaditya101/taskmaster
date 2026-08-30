package com.example.taskmaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(
            DuplicateResourceException ex
    ) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "DUPLICATE_RESOURCE",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
}