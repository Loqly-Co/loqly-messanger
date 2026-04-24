package com.wasd.messenger.controller.advice;

import com.wasd.messenger.data.response.ErrorResponse;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExistsException(ServletException e) {
        ErrorResponse errorResponse = new ErrorResponse(
            500,
            e.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExistsException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(
            400,
            e.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUserWrongAuthCredentialsException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(
            401,
            e.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}