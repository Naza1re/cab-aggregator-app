package com.example.driverservice.exception;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException(String s) {
        super(s);
    }
}
