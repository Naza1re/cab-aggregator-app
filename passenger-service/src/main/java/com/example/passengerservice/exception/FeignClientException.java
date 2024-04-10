package com.example.passengerservice.exception;

public class FeignClientException extends RuntimeException {
    public FeignClientException(String s) {
        super(s);
    }
}
