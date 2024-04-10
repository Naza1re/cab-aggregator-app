package com.example.passengerservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {
    public static final String PASSENGER_NOT_FOUND_EXCEPTION = "Passenger with id ' %s' not found";
    public static final String INVALID_TYPE_OF_SORT = "Invalid type of sort";
    public static final String PASSENGER_WITH_EMAIL_ALREADY_EXIST = "Passenger with email ' %s' already exist";
    public static final String PASSENGER_WITH_PHONE_ALREADY_EXIST = "Passenger with phone ' %s' already exist";
    public static final String PAGINATION_FORMAT_EXCEPTION = "Invalid param of pagination";
    public static final String RATING_SERVICE_IS_NOT_AVAILABLE = "Rating service is not available , try later";
}
