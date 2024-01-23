package com.example.rideservice.exception;

public class RideNotFoundException extends RuntimeException {
    public RideNotFoundException(String s) {
        super(s);
    }
}
