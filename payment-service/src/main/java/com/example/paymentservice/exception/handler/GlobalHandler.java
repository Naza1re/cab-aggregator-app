package com.example.paymentservice.exception.handler;

import com.example.paymentservice.exception.*;
import com.example.paymentservice.exception.error.AppError;
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

    @ExceptionHandler({CustomerNotFoundException.class,NotFoundException.class})
    public ResponseEntity<AppError> handleNotFoundException(Exception ex) {
        String errorMessage = ex.getMessage();
        log.info("NotFoundException handler was called. Exception message : "+errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler({BalanceException.class, CreateCustomerException.class, GenerateTokenException.class, PaymentException.class, ServiceUnAvailableException.class, FeignClientException.class})
    public ResponseEntity<AppError> handleBadRequestException(Exception ex) {
        String errorMessage = ex.getMessage();
        log.info("BadRequest handler was called. Exception message : "+errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<AppError> handleConflictException(Exception ex) {
        String errorMessage = ex.getMessage();
        log.info("ConflictException handler was called. Exception message : "+errorMessage);
        return ResponseEntity.status(HttpStatus.CONFLICT)
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
