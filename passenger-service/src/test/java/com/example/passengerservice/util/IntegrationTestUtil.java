package com.example.passengerservice.util;

import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.response.PassengerResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IntegrationTestUtil {
    public static final String PATH_PARAM_ID = "id";
    public static final Long ID_NOT_FOUND = 100L;
    public static final Long DEFAULT_ID = 1L;
    public static final String PATH_ID = "api/v1/passengers/{id}";
    public static final String FIND_ALL = "api/v1/passengers";
    public static final String PATH_DEFAULT = "api/v1/passengers";
    public static final Long DEFAULT_CREATE_ID = 4L;
    public static final String DEFAULT_NAME = "Johnn";
    public static final String DEFAULT_SURNAME = "Doe";
    public static final String DEFAULT_EMAIL = "mcarimm@mail.ru";
    public static final String DEFAULT_PHONE = "16344038971";
    public static final String NEW_NAME = "nikolai";
    public static final String NEW_SURNAME = "sobolev";
    public static final String NEW_PHONE = "16304838971";
    public static final String NEW_EMAIL = "naza1re@mail.ru";

    public PassengerRequest getUpdatePassengerRequest() {
        return PassengerRequest.builder()
                .name(NEW_NAME)
                .surname(NEW_SURNAME)
                .email(NEW_EMAIL)
                .phone(NEW_PHONE)
                .build();

    }

    public PassengerResponse getExpectedUpdatePassengerResponse() {
        return PassengerResponse.builder()
                .id(DEFAULT_ID)
                .name(NEW_NAME)
                .surname(NEW_SURNAME)
                .email(NEW_EMAIL)
                .phone(NEW_PHONE)
                .build();
    }

    public PassengerResponse getCreatePassengerResponse() {
        return PassengerResponse.builder()
                .id(DEFAULT_CREATE_ID)
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .email(DEFAULT_EMAIL)
                .phone(DEFAULT_PHONE)
                .build();

    }

    public PassengerRequest getCreatePassengerRequest() {
        return PassengerRequest.builder()
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .email(DEFAULT_EMAIL)
                .phone(DEFAULT_PHONE)
                .build();

    }
}
