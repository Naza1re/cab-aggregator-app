package com.modsen.promocodeservice.exceptions.handler;

import com.modsen.promocodeservice.exceptions.PromoCodeAllReadyExistException;
import com.modsen.promocodeservice.exceptions.PromoCodeNotFoundException;
import com.modsen.promocodeservice.exceptions.error.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PromoCodeNotFoundException.class)
    public ResponseEntity<AppError> handlerNotFoundException(RuntimeException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new AppError(errorMessage));
    }

    @ExceptionHandler(PromoCodeAllReadyExistException.class)
    public ResponseEntity<AppError> handlerAlreadyExistException(RuntimeException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new AppError(errorMessage));
    }
}
