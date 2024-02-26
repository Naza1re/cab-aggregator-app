package com.example.driverservice.exception;

public class FeignClientException extends RuntimeException {
    public FeignClientException(String s) {
        super(s);
    }
}
