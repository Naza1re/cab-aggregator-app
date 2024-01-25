package com.example.rideservice.exception;

public class RideAlreadyAcceptedException extends RuntimeException {
    public RideAlreadyAcceptedException(String s){
        super(s);
    }
}
