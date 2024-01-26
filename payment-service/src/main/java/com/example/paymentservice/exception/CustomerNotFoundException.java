package com.example.paymentservice.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String s){
        super(s);
    }

}
