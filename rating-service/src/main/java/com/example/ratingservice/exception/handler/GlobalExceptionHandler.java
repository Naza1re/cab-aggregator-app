package com.example.ratingservice.exception.handler;

import com.example.ratingservice.exception.DriverRatingAlreadyExistException;
import com.example.ratingservice.exception.DriverRatingNotFoundException;
import com.example.ratingservice.exception.PassengerRatingAlreadyExistException;
import com.example.ratingservice.exception.PassengerRatingNotFoundException;
import com.example.ratingservice.exception.appError.AppError;
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

    @ExceptionHandler({DriverRatingAlreadyExistException.class, PassengerRatingAlreadyExistException.class})
    public ResponseEntity<AppError> handleCustomBadRequestException(RuntimeException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({DriverRatingNotFoundException.class, PassengerRatingNotFoundException.class})
    public ResponseEntity<AppError> handleCustomNotFoundException(RuntimeException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.NOT_FOUND);
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
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
