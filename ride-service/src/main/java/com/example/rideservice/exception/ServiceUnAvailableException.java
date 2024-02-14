package com.example.rideservice.exception;

public class ServiceUnAvailableException extends RuntimeException {
    public ServiceUnAvailableException(String s) {
        super(s);
    }
}
