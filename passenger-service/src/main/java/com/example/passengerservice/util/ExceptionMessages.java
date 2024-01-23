package com.example.passengerservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {
    public final String PASSENGER_NOT_FOUND_EXCEPTION = "Passenger with id ' %s' not found";
    public final String INVALID_TYPE_OF_SORT = "Invalid type of sort";
    public final String PASSENGER_WITH_EMAIL_ALREADY_EXIST = "Passenger with email ' %s' already exist";
    public final String PASSENGER_WITH_PHONE_ALREADY_EXIST = "Passenger with phone ' %s' already exist";
    public final String PAGINATION_FORMAT_EXCEPTION = "Invalid param of pagination";
}
