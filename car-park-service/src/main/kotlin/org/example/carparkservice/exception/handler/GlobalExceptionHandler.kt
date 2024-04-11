package org.example.carparkservice.exception.handler

import org.example.carparkservice.exception.CarAlreadyExistException
import org.example.carparkservice.exception.CarNotFoundException
import org.example.carparkservice.exception.apperror.AppError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {


    @ExceptionHandler(CarAlreadyExistException::class)
    fun handleCarAlreadyExistException(e: CarAlreadyExistException): ResponseEntity<AppError> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(AppError(e.message.toString()))
    }

    @ExceptionHandler(CarNotFoundException::class)
    fun handleCarNotFoundException(e: CarNotFoundException): ResponseEntity<AppError> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(AppError(e.message.toString()))
    }
}