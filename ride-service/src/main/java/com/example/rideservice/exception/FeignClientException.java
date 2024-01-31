package com.example.rideservice.exception;

public class FeignClientException extends RuntimeException {
    public FeignClientException(String s) {
        super(s);
    }
}
