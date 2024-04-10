package com.example.passengerservice.exception;

public class ServiceUnAvailableException extends RuntimeException {
    public ServiceUnAvailableException(String s) {
        super(s);
    }
}
