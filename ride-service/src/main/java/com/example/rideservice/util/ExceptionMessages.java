package com.example.rideservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {

    public static final String RIDE_NOT_FOUND_EXCEPTION = "Ride with id ' %s' not found";
    public static final String RIDE_DONT_HAVE_DRIVER_TO_START = "Ride with id ' %s' dont have driver";
    public static final String PAGINATION_FORMAT_EXCEPTION = "Invalid pagination format";
    public static final String INVALID_TYPE_OF_SORT = "Invalid sort type";
    public static final String DRIVER_SERVICE_IS_NOT_AVAILABLE = "Driver service is not available, try later";
    public static final String PASSENGER_SERVICE_IS_NOT_AVAILABLE = "Passenger service is not available, try later";
    public static final String PAYMENT_SERVICE_IS_NOT_AVAILABLE = "Payment service is not available, try later";
    public static final String PROMO_CODE_SERVICE_IS_NOT_AVAILABLE = "Promo code service is not available, try later";
    public static final String PRICE_SERVICE_IS_NOT_AVAILABLE = "Price service is not available, try later";



}
