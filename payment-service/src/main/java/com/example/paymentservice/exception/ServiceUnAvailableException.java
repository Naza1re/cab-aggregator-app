package com.example.paymentservice.exception;

public class ServiceUnAvailableException extends RuntimeException {
    public ServiceUnAvailableException(String s) {
        super(s);
    }
}
