package com.example.ratingservice.exception;

public class DriverRatingAlreadyExistException extends RuntimeException {
    public DriverRatingAlreadyExistException(String s){
        super(s);
    }
}
