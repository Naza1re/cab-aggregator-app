package com.example.rideservice.exception;

public class SomethingWrongTemperatureServiceException  extends RuntimeException {
    public SomethingWrongTemperatureServiceException(String s) {
        super(s);
    }
}
