package com.example.driverservice.exception;

public class PhoneAlreadyExistException extends RuntimeException {
    public PhoneAlreadyExistException(String s) {
        super(s);
    }
}
