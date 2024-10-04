package com.example.api.utils;

import com.example.api.dto.ExceptionDto;
import com.example.api.exception.DBException;
import com.example.api.exception.EntityNotFoundException;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleResourceNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionDto(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidationException() {
        return new ResponseEntity<>(new ExceptionDto("Validation failed!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionDto> handleNoResource() {
        return new ResponseEntity<>(new ExceptionDto("Page not found!"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DBException.class)
    public ResponseEntity<ExceptionDto> handleDB() {
        return new ResponseEntity<>(new ExceptionDto("Database error!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ExceptionDto> handleAuth() {
        return new ResponseEntity<>(new ExceptionDto("Password is incorrect!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnirestException.class)
    public ResponseEntity<ExceptionDto> handleUnirest() {
        return new ResponseEntity<>(new ExceptionDto("Failed to generate access token!"), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGenericException(Exception ex) {
        return new ResponseEntity<>(new ExceptionDto(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
