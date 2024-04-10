package com.example.rideservice.exception;

public class RideNotHaveDriverException extends RuntimeException {
    public RideNotHaveDriverException(String s) {
        super(s);
    }
}
