package com.example.passengerservice.exception.handler;

import com.example.passengerservice.exception.*;
import com.example.passengerservice.exception.appError.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PassengerNotFoundException.class, NotFoundException.class})
    public ResponseEntity<AppError> handlePassengerNotFoundException(
            PassengerNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler({PhoneAlreadyExistException.class, EmailAlreadyExistException.class})
    public ResponseEntity<AppError> handleConflictException(
            RuntimeException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler({SortTypeException.class, PaginationParamException.class, RatingException.class})
    public ResponseEntity<AppError> handleBadRequestException(
            RuntimeException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

}

