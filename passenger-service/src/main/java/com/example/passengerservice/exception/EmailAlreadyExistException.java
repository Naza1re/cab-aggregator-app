package com.example.passengerservice.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String s){
        super(s);
    }
}
