package com.example.paymentservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {

    public static final String CUSTOMER_ALREADY_EXIST = "Customer with id ' %s' already exist";
    public static final String CUSTOMER_NOT_FOUND_EXCEPTION = "Customer with id ' %s' not found";
    public static final String NOT_ENOUGH_MONEY = "Not enough money on your bank card";
    public static String PASSENGER_SERVICE_IS_NOT_AVAILABLE = "Passenger service is not available , try later";
}
