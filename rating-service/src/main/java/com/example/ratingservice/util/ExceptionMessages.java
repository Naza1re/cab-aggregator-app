package com.example.ratingservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {

    public static final String DRIVER_RATING_NOT_FOUND = "Driver rating with driver id ' %s' not found";
    public static final String DRIVER_RATING_ALREADY_EXIST = "Driver rating with id driver id ' %s' already exist";
    public static final String PASSENGER_RATING_ALREADY_EXIST = "Passenger rating with passenger id ' %s' already exist";
    public static final String PASSENGER_RATING_NOT_FOUND = "Passenger rating with passenger id ' %s' not found";
}
