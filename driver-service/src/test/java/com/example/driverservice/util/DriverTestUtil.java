package com.example.driverservice.util;

import com.example.driverservice.dto.request.DriverRequest;
import com.example.driverservice.dto.response.DriverListResponse;
import com.example.driverservice.dto.response.DriverResponse;
import com.example.driverservice.model.Driver;
import lombok.experimental.UtilityClass;

import java.util.List;

public class DriverTestUtil {
    public static final Long DEFAULT_ID = 1L;
    public static final Long NOT_FOUND_ID = 100L;
    public static final String DEFAULT_NAME = "James";
    public static final String DEFAULT_SURNAME = "Bond";
    public static final String DEFAULT_EMAIL = "mcarim@mail.ru";
    public static final String DEFAULT_PHONE = "3752567890";
    public static final String DEFAULT_COLOR = "red";
    public static final String DEFAULT_NUMBER = "1234 KX-5";
    public static final String DEFAULT_MODEL = "Reno logan";
    public static final Long SECOND_ID = 2L;
    public static final String UPDATE_NAME = "Nick";
    public static final String UPDATE_SURNAME = "Areturyan";
    public static final String UPDATE_EMAIL = "naza1re@mail.ru";
    public static final String UPDATE_PHONE = "37525676790";
    public static final String UPDATE_COLOR = "white";
    public static final String UPDATE_NUMBER = "1236 KX-5";
    public static final String UPDATE_MODEL = "skoda rapid";
    public static final int INVALID_PAGINATION_SIZE = -1;
    public static final int DEFAULT_PAGINATION_SIZE = 1;
    public static final int INVALID_PAGINATION_PAGE = -1;
    public static final int DEFAULT_PAGINATION_PAGE = 1;
    public static final String INVALID_PAGINATION_SORTED_TYPE = "mmm";


    public static final boolean AVAILABLE = true;


    public static DriverListResponse getDriverListResponse() {
        return DriverListResponse.builder()
                .driverResponseList(List.of(getDefaultDriverResponse(), getSecondDriverResponse()))
                .build();
    }

    public static List<Driver> getDriverList() {
        return List.of(getDefaultDriver(), getSecondDriver());
    }

    public static Driver getNotSavedDriver() {
        return Driver.builder()
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .color(DEFAULT_COLOR)
                .available(AVAILABLE)
                .email(DEFAULT_EMAIL)
                .phone(DEFAULT_PHONE)
                .model(DEFAULT_MODEL)
                .number(DEFAULT_NUMBER)
                .build();

    }

    public static DriverResponse getUpdateDriverResponse() {
        return DriverResponse.builder()
                .id(DEFAULT_ID)
                .name(UPDATE_NAME)
                .surname(UPDATE_SURNAME)
                .color(UPDATE_COLOR)
                .available(AVAILABLE)
                .email(UPDATE_EMAIL)
                .phone(UPDATE_PHONE)
                .model(UPDATE_MODEL)
                .number(UPDATE_NUMBER)
                .build();
    }

    public static Driver getUpdateDriver() {
        return Driver.builder()
                .id(DEFAULT_ID)
                .name(UPDATE_NAME)
                .surname(UPDATE_SURNAME)
                .color(UPDATE_COLOR)
                .available(AVAILABLE)
                .email(UPDATE_EMAIL)
                .phone(UPDATE_PHONE)
                .model(UPDATE_MODEL)
                .number(UPDATE_NUMBER)
                .build();

    }

    public static Driver getSecondDriver() {
        return Driver.builder()
                .id(SECOND_ID)
                .name(UPDATE_NAME)
                .surname(UPDATE_SURNAME)
                .color(UPDATE_COLOR)
                .available(AVAILABLE)
                .email(UPDATE_EMAIL)
                .phone(UPDATE_PHONE)
                .model(UPDATE_MODEL)
                .number(UPDATE_NUMBER)
                .build();
    }

    public static Driver getSavedDriver() {
        return Driver.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .color(DEFAULT_COLOR)
                .available(AVAILABLE)
                .email(DEFAULT_EMAIL)
                .phone(DEFAULT_PHONE)
                .model(DEFAULT_MODEL)
                .number(DEFAULT_NUMBER)
                .build();
    }

    public static Driver getDefaultDriver() {
        return Driver.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .color(DEFAULT_COLOR)
                .available(AVAILABLE)
                .email(DEFAULT_EMAIL)
                .phone(DEFAULT_PHONE)
                .model(DEFAULT_MODEL)
                .number(DEFAULT_NUMBER)
                .build();
    }

    public static DriverResponse getSecondDriverResponse() {
        return DriverResponse.builder()
                .id(SECOND_ID)
                .name(UPDATE_NAME)
                .surname(UPDATE_SURNAME)
                .color(UPDATE_COLOR)
                .available(AVAILABLE)
                .email(UPDATE_EMAIL)
                .phone(UPDATE_PHONE)
                .model(UPDATE_MODEL)
                .number(UPDATE_NUMBER)
                .build();
    }

    public static DriverResponse getDefaultDriverResponse() {
        return DriverResponse.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .color(DEFAULT_COLOR)
                .available(AVAILABLE)
                .email(DEFAULT_EMAIL)
                .phone(DEFAULT_PHONE)
                .model(DEFAULT_MODEL)
                .number(DEFAULT_NUMBER)
                .build();
    }

    public static DriverRequest getDriverUpdateRequest() {
        return DriverRequest.builder()
                .name(UPDATE_NAME)
                .surname(UPDATE_SURNAME)
                .color(UPDATE_COLOR)
                .email(UPDATE_EMAIL)
                .phone(UPDATE_PHONE)
                .model(UPDATE_MODEL)
                .number(UPDATE_NUMBER)
                .build();
    }

    public static DriverRequest getDriverRequest() {
        return DriverRequest.builder()
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .color(DEFAULT_COLOR)
                .email(DEFAULT_EMAIL)
                .phone(DEFAULT_PHONE)
                .model(DEFAULT_MODEL)
                .number(DEFAULT_NUMBER)
                .build();

    }

    public static DriverRequest getDriverRequest(String existParam) {
        return DriverRequest.builder()
                .name(DEFAULT_NAME)
                .surname(DEFAULT_SURNAME)
                .color(DEFAULT_COLOR)
                .email(existParam)
                .phone(existParam)
                .model(DEFAULT_MODEL)
                .number(existParam)
                .build();

    }
}
