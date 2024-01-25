package com.example.ratingservice.exception;

public class PassengerRatingAlreadyExistException extends RuntimeException {
    public PassengerRatingAlreadyExistException(String s) {
        super(s);
    }
}
