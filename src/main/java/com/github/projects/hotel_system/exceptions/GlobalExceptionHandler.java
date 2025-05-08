package com.github.projects.hotel_system.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidUserRequestException.class)
    public ResponseEntity<?> handleInvalidUserRequest(InvalidUserRequestException ex) {

        return ResponseEntity.badRequest()
            .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {

        return ResponseEntity.badRequest()
            .body(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(InvalidCreationRequestException.class)
    public ResponseEntity<?> handleInvalidRequest(InvalidCreationRequestException ex) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        
        List<FieldListerResponse> listOfErrors = ex.getBindingResult()
            .getFieldErrors().stream()
            .map(error -> new FieldListerResponse(error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());

        return ResponseEntity.badRequest()
            .body(new ErrorResponse(
                "Validation failed!!",
                HttpStatus.BAD_REQUEST.value(),
                listOfErrors
            ));

    }
    
}
