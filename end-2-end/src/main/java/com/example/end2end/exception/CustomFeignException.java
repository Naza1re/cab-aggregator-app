package com.example.end2end.exception;

public class CustomFeignException extends RuntimeException {
    public CustomFeignException(String s) {
        super(s);
    }
}
