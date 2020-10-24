package com.searchitemsapp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.searchitemsapp.exception.dto.ErrorDetailsDto;

import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
	
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(LocalDateTime.now(), ex.getMessage(), request.getDescription(false),HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ConfilctFoundException.class)
    public ResponseEntity<ErrorDetailsDto> conflictFoundException(ConfilctFoundException ex, WebRequest request) {
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(LocalDateTime.now(), ex.getMessage(), request.getDescription(false),HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }


}
