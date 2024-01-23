package com.example.driverservice.exception;

public class CarNumberAlreadyExistException extends RuntimeException {
    public CarNumberAlreadyExistException(String s) {
        super(s);
    }
}
