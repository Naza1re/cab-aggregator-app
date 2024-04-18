package org.example.pricecalculatorservice.exception.handler

import org.example.pricecalculatorservice.exception.NotFoundException
import org.example.pricecalculatorservice.exception.SomethingWrongTemperatureServiceException
import org.example.pricecalculatorservice.exception.appError.AppError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: RuntimeException): ResponseEntity<AppError> {
        val message = e.message
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message?.let { AppError(it) })
    }


    @ExceptionHandler(SomethingWrongTemperatureServiceException::class)
    fun handlerSomethingWrongTemperatureServiceException(e: RuntimeException): ResponseEntity<AppError> {
        val message = e.message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message?.let { AppError(it) })
    }
}