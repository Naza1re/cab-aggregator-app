package com.example.ratingservice.exception.handler;

import com.example.ratingservice.exception.DriverRatingAlreadyExistException;
import com.example.ratingservice.exception.DriverRatingNotFoundException;
import com.example.ratingservice.exception.PassengerRatingAlreadyExistException;
import com.example.ratingservice.exception.PassengerRatingNotFoundException;
import com.example.ratingservice.exception.appError.AppError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DriverRatingAlreadyExistException.class, PassengerRatingAlreadyExistException.class})
    public ResponseEntity<AppError> handleCustomBadRequestException(RuntimeException ex) {
        String errorMessage = ex.getMessage();
        log.info("ConflictException handler was called. Exception message : "+errorMessage);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler({DriverRatingNotFoundException.class, PassengerRatingNotFoundException.class})
    public ResponseEntity<AppError> handleCustomNotFoundException(RuntimeException ex) {
        String errorMessage = ex.getMessage();
        log.info("NotFoundException handler was called. Exception message : "+errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
