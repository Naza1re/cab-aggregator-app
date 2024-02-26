package com.example.paymentservice.exception;

public class FeignClientException extends RuntimeException {
    public FeignClientException(String s) {
        super(s);
    }
}
