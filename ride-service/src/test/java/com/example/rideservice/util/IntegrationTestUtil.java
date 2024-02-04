package com.example.rideservice.util;

import com.example.rideservice.dto.request.RideRequest;
import com.example.rideservice.dto.response.RideResponse;
import com.example.rideservice.model.enums.Status;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

@UtilityClass
public class IntegrationTestUtil {
    public static final Long DEFAULT_ID = 1L;
    public static final String PATH_DEFAULT = "api/v1/rides";
    public static final String PATH_ID = "api/v1/rides/{id}";
    public static final String PATH_START = "api/v1/rides/{id}/start";
    public static final String PATH_END = "api/v1/rides/{id}/end";
    public static final String PATH_GET_BY_PASSENGER = "api/v1/rides/passenger/{id}";
    public static final String PATH_GET_BY_DRIVER = "api/v1/rides/driver/{id}";
    public static final String ID = "id";
    public static final String PATH_PAGE = "api/v1/rides/page";
    public static final String PAGE_PARAM_NAME = "offset";
    public static final String SIZE_PARAM_NAME = "limit";
    public static final String ORDER_BY_PARAM_NAME = "type";
    public static final int VALID_OFFSET = 1;
    public static final int VALID_LIMIT = 10;
    public static final String VALID_TYPE = "price";
    public static final Long CREATED_ID = 3L;
    public static final Long PASSENGER_ID = 15L;
    public static final LocalDateTime DEFAULT_START_DATE = LocalDateTime.of(2012, Month.SEPTEMBER, 20, 8, 46, 26);
    public static final String DROP_OFF_ADDRESS = "Фрунзе 33";
    public static final String PICK_UP_ADDRESS = "Московский 33";
    public static final Status STATUS_CREATED = Status.CREATED;
    public static final String INSTRUCTIONS = "Нас четверо";
    public static final BigDecimal PRICE = BigDecimal.valueOf(10);

    public RideRequest getDefaultRideRequest() {
        return RideRequest.builder()
                .passengerId(PASSENGER_ID)
                .pickUpAddress(PICK_UP_ADDRESS)
                .dropOffAddress(DROP_OFF_ADDRESS)
                .instructions(INSTRUCTIONS)
                .build();
    }

    public RideResponse getDefaultRideResponse() {
        return RideResponse.builder()
                .id(CREATED_ID)
                .passengerId(PASSENGER_ID)
                .dropOffAddress(DROP_OFF_ADDRESS)
                .pickUpAddress(PICK_UP_ADDRESS)
                .price(PRICE)
                .status(STATUS_CREATED)
                .instructions(INSTRUCTIONS)
                .build();
    }

}
