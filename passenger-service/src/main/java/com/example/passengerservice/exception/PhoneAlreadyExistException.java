package com.example.passengerservice.exception;

public class PhoneAlreadyExistException extends RuntimeException{
    public PhoneAlreadyExistException(String s){
        super(s);
    }
}
