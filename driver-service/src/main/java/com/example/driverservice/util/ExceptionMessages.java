package com.example.driverservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {

    public static final String PAGINATION_FORMAT_EXCEPTION = "Invalid pagination";
    public static final String INVALID_TYPE_OF_SORT = "Invalid type of sort";
    public static final String DRIVER_NOT_FOUND_EXCEPTION = "Driver with id ' %s' not found";
    public static final String DRIVER_WITH_EMAIL_ALREADY_EXIST = "Driver with id ' %s' already exist";
    public static final String DRIVER_WITH_PHONE_ALREADY_EXIST = "Driver with phone ' %s' already exist";
    public static final String DRIVER_WITH_CAR_NUMBER_ALREADY_EXIST = "Driver with car number ' %s' already exist";
    public static final String RATING_SERVICE_IS_NOT_AVAILABLE = "Rating service is not available , try later";

}
