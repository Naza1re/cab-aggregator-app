package com.example.driverservice.util;

import com.example.driverservice.dto.request.DriverRequest;
import com.example.driverservice.dto.response.DriverResponse;

public class IntegrationTestUtil {

    public static final String PATH_PARAM_ID = "id";
    public static final Long ID_NOT_FOUND = 100L;
    public static final Long DEFAULT_ID = 1L;
    public static final String PATH_ID = "api/v1/drivers/{id}";
    public static final String CHANGE_STATUS = "api/v1/drivers/{id}/status";
    public static final String FIND_ALL = "api/v1/drivers";
    public static final String PATH_DEFAULT = "api/v1/drivers";
    public static final Long DEFAULT_CREATE_ID = 4L;
    public static final String DEFAULT_NAME = "Johnn";
    public static final String DEFAULT_SURNAME = "Doe";
    public static final String DEFAULT_NUMBER = "3850 KX-5";
    public static final String DEFAULT_NUMBER_FOR_CHANGE_STATUS = "3856 KX-5";
    public static final String DEFAULT_COLOR = "Red";
    public static final String DEFAULT_EMAIL = "mcarimm@mail.ru";
    public static final String DEFAULT_EMAIL_FOR_CHANGE_STATUS = "mcarim@mail.ru";
    public static final String DEFAULT_PHONE = "16344038971";
    public static final String DEFAULT_PHONE_FOR_CHANGE_STATUS = "16344838971";
    public static final String DEFAULT_MODEL = "Passat";
    public static final boolean DEFAULT_AVAILABLE_CHANGE = false;
    public static final String NEW_NAME = "nikolai";
    public static final String NEW_SURNAME = "sobolev";
    public static final String NEW_PHONE = "16304838971";
    public static final String NEW_EMAIL = "naza1re@mail.ru";
    public static final String NEW_NUMBER = "naza1re@mail.ru";

    public static DriverRequest getUpdateDriverRequest() {
        return DriverRequest.builder()
                .model(DEFAULT_MODEL)
                .color(DEFAULT_COLOR)
                .number(NEW_NUMBER)
                .name(NEW_NAME)
                .surname(NEW_SURNAME)
                .email(NEW_EMAIL)
                .phone(NEW_PHONE)
                .build();

    }

    public static DriverResponse getExpectedUpdateDriverResponse() {
        return DriverResponse.builder()
                .id(DEFAULT_ID)
                .available(DEFAULT_AVAILABLE_CHANGE)
                .model(DEFAULT_MODEL)
                .color(DEFAULT_COLOR)
                .number(NEW_NUMBER)
                .name(NEW_NAME)
                .surname(NEW_SURNAME)
                .email(NEW_EMAIL)
                .phone(NEW_PHONE)
                .build();
    }

    public static DriverResponse getCreateDriverResponse() {
        return DriverResponse.builder()
                .id(DEFAULT_CREATE_ID)
                .model(DEFAULT_MODEL)
                .color(DEFAULT_COLOR)
                .number(DEFAULT_NUMBER)
                .name(DEFAULT_NAME)
                .available(DEFAULT_AVAILABLE_CHANGE)
                .surname(DEFAULT_SURNAME)
                .email(DEFAULT_EMAIL)
                .phone(DEFAULT_PHONE)
                .build();

    }

    public static DriverResponse getChangeStatusDriverResponse() {
        return DriverResponse.builder()
                .id(DEFAULT_ID)
                .model(DEFAULT_MODEL)
                .color(DEFAULT_COLOR)
                .number(DEFAULT_NUMBER_FOR_CHANGE_STATUS)
                .name(DEFAULT_NAME)
                .available(DEFAULT_AVAILABLE_CHANGE)
                .surname(DEFAULT_SURNAME)
                .email(DEFAULT_EMAIL_FOR_CHANGE_STATUS)
                .phone(DEFAULT_PHONE_FOR_CHANGE_STATUS)
                .build();

    }

    public static DriverRequest getCreateDriverRequest() {
        return DriverRequest.builder()
                .model(DEFAULT_MODEL)
                .color(DEFAULT_COLOR)
                .number(DEFAULT_NUMBER)
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .email(DEFAULT_EMAIL)
                .phone(DEFAULT_PHONE)
                .build();

    }

}
