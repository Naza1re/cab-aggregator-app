package com.example.rideservice.exception.handler;

import com.example.rideservice.exception.*;
import com.example.rideservice.exception.appError.AppError;
import feign.RetryableException;
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

    @ExceptionHandler({RideNotFoundException.class, NotFoundException.class})
    public ResponseEntity<AppError> handleRideNotFoundException(Exception ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler({RideHaveDriverException.class, RideAlreadyAcceptedException.class, RideNotHaveDriverException.class})
    public ResponseEntity<AppError> handleCustomException(Exception ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler({ServiceUnAvailableException.class, PaginationParamException.class, SortTypeException.class, FeignClientException.class, RetryableException.class})
    public ResponseEntity<AppError> handleBadRequestException(Exception ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}
