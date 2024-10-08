package com.example.driverservice.exception.handler;

import com.example.driverservice.exception.FeignClientException;
import com.example.driverservice.exception.DriverNotFoundException;
import com.example.driverservice.exception.NotFoundException;
import com.example.driverservice.exception.PhoneAlreadyExistException;
import com.example.driverservice.exception.EmailAlreadyExistException;
import com.example.driverservice.exception.CarNumberAlreadyExistException;
import com.example.driverservice.exception.RatingException;
import com.example.driverservice.exception.SortTypeException;
import com.example.driverservice.exception.ServiceUnAvailableException;
import com.example.driverservice.exception.PaginationParamException;
import com.example.driverservice.exception.error.AppError;
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
public class GlobalHandler {

    @ExceptionHandler({DriverNotFoundException.class, NotFoundException.class})
    public ResponseEntity<AppError> handleDriverNotFoundException(Exception ex) {
        String errorMessage = ex.getMessage();
        log.info("NotFoundException handler was called. Exception message : " + errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler({PhoneAlreadyExistException.class, EmailAlreadyExistException.class, CarNumberAlreadyExistException.class})
    public ResponseEntity<AppError> handleConflictException(Exception ex) {
        String errorMessage = ex.getMessage();
        log.info("ConflictException handler was called. Exception message : " + errorMessage);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler({ServiceUnAvailableException.class, FeignClientException.class, RatingException.class, SortTypeException.class, PaginationParamException.class})
    public ResponseEntity<AppError> handleBadRequestException(Exception ex) {
        String errorMessage = ex.getMessage();
        log.info("BadRequest handler was called. Exception message : " + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

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
