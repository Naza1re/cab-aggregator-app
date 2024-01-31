package com.example.passengerservice.util;

import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.request.RatingRequest;
import com.example.passengerservice.dto.response.PassengerRatingResponse;
import com.example.passengerservice.dto.response.PassengerResponse;
import com.example.passengerservice.model.Passenger;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PassengerTestUtil {
    public static final Long DEFAULT_ID = 1L;
    public static final Long NOT_FOUND_ID = 2L;
    public static final String DEFAULT_NAME = "James";
    public static final String DEFAULT_SURNAME = "Bond";
    public static final String DEFAULT_EMAIL = "mcarim@mail.ru";
    public static final String DEFAULT_PHONE = "3752567890";
    public static final String NAME_FOR_UPDATE = "Nick";
    public static final String SURNAME_FOR_UPDATE = "Smith";
    public static final String PHONE_FOR_UPDATE = "37525645678";
    public static final String EMAIL_OR_UPDATE = "mcarima@mail.ru";
    public static final Long DEFAULT_PASSENGER_RATE = 5L;
    public static final Long DEFAULT_ID_RESPONSE = 1L;
    public static final int INVALID_PARAM_SIZE = -1;
    public static final int INVALID_PARAM_PAGE = -1;
    public static final String INVALID_PARAM_ORDER_BY = "mmm";


    public PassengerResponse getPassengerResponse() {
        return PassengerResponse.builder()
                .id(DEFAULT_ID)
                .email(DEFAULT_EMAIL)
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .phone(DEFAULT_PHONE)
                .build();
    }

    public Passenger getPassenger() {
        return Passenger.builder()
                .id(DEFAULT_ID)
                .email(DEFAULT_EMAIL)
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .phone(DEFAULT_PHONE)
                .build();
    }

    public PassengerRatingResponse getPassengerRatingResponse() {
        return PassengerRatingResponse.builder()
                .id(DEFAULT_ID_RESPONSE)
                .passenger(DEFAULT_ID)
                .rate(DEFAULT_PASSENGER_RATE)
                .build();
    }

    public RatingRequest getPassengerRatingRequest() {
        return RatingRequest.builder()
                .id(DEFAULT_ID)
                .build();
    }

    public Passenger getNotSavedPassenger() {
        return Passenger.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .phone(DEFAULT_PHONE)
                .email(DEFAULT_EMAIL)
                .build();
    }

    public PassengerRequest getPassengerRequest() {
        return PassengerRequest.builder()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .phone(DEFAULT_PHONE)
                .build();
    }

    public PassengerRequest getUpdatePassengerRequest() {
        return PassengerRequest.builder()
                .name(NAME_FOR_UPDATE)
                .email(EMAIL_OR_UPDATE)
                .phone(PHONE_FOR_UPDATE)
                .surname(SURNAME_FOR_UPDATE)
                .build();
    }

    public PassengerResponse getUpdatePassengerResponse() {
        return PassengerResponse.builder()
                .name(NAME_FOR_UPDATE)
                .surname(SURNAME_FOR_UPDATE)
                .email(EMAIL_OR_UPDATE)
                .phone(PHONE_FOR_UPDATE)
                .build();
    }

    public Passenger getUpdatePassenger() {
        return Passenger.builder()
                .name(NAME_FOR_UPDATE)
                .surname(SURNAME_FOR_UPDATE)
                .phone(PHONE_FOR_UPDATE)
                .email(EMAIL_OR_UPDATE)
                .build();
    }


}
