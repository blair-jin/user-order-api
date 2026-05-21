package com.example.userorder.common.exception;

import com.example.userorder.dto.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            UserNotFoundException.class,
            UserProfileNotFoundException.class,
            OrderNotFoundException.class,
            OrderItemNotFoundException.class,
            ProductNotFoundException.class,
            CartNotFoundException.class,
            CartItemNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateLoginIdException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(RuntimeException e) {
        ErrorResponse response = new ErrorResponse((HttpStatus.CONFLICT.value()), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleInvalid(RuntimeException e) {
        ErrorResponse response = new ErrorResponse((HttpStatus.BAD_REQUEST.value()), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}