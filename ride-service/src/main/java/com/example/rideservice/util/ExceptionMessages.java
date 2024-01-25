package com.example.rideservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {

    public static final String RIDE_NOT_FOUND_EXCEPTION = "Ride with id ' %s' not found";
    public static final String RIDE_DONT_HAVE_DRIVER_TO_START = "Ride with id ' %s' dont have driver";
    public static final String PAGINATION_FORMAT_EXCEPTION = "Invalid pagination format";
    public static final String INVALID_TYPE_OF_SORT = "Invalid sort type";



}
