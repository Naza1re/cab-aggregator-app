package com.example.rideservice.util;

import com.example.rideservice.dto.request.RideRequest;
import com.example.rideservice.dto.response.*;
import com.example.rideservice.model.Ride;
import com.example.rideservice.model.enums.Status;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@UtilityClass
public class RideTestUtil {
    public static final String PAYMENT_ID = "sk_1312312";
    public static final String CURRENCY = "USD";
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(10);
    public static final Long PASSENGER_ID = 1L;
    public static final String PASSENGER_NAME = "Nikita";
    public static final String PASSENGER_SURNAME = "Arkadev";
    public static final String PASSENGER_PHONE = "+375259148179";
    public static final String PASSENGER_EMAIL = "mcarim@mail.ru";
    public static final Long SECOND_ID = 1L;
    public static final LocalDateTime SECOND_START_DATE = LocalDateTime.of(2011, Month.SEPTEMBER, 20, 8, 46, 26);
    public static final LocalDateTime SECOND_END_DATE = LocalDateTime.of(2011, Month.SEPTEMBER, 20, 8, 15, 0);
    public static final BigDecimal SECOND_PRICE = BigDecimal.valueOf(10);
    public static final String SECOND_PICK_UP_ADDRESS = "Фрунзе 33";
    public static final String SECOND_DROP_OFF_ADDRESS = "Московский 33";
    public static final Long SECOND_DRIVER_ID = 1L;
    public static final Long SECOND_PASSENGER_ID = 1L;
    public static final String SECOND_INSTRUCTIONS = "Нас пятеро , один в багажник";
    public static final Status SECOND_STATUS = Status.CREATED;
    public static final Long DEFAULT_ID = 1L;
    public static final LocalDateTime DEFAULT_START_DATE = LocalDateTime.of(2012, Month.SEPTEMBER, 20, 8, 46, 26);
    public static final LocalDateTime DEFAULT_END_DATE = LocalDateTime.of(2012, Month.SEPTEMBER, 20, 8, 15, 0);
    public static final BigDecimal DEFAULT_PRICE = BigDecimal.valueOf(10);
    public static final String DEFAULT_PICK_UP_ADDRESS = "Фрунзе 33";
    public static final String DEFAULT_DROP_OFF_ADDRESS = "Московский 33";
    public static final Long DEFAULT_DRIVER_ID = 1L;
    public static final Long DEFAULT_PASSENGER_ID = 1L;
    public static final String DEFAULT_INSTRUCTIONS = "Нас пятеро , один в багажник";
    public static final Status CREATED_STATUS = Status.CREATED;
    public static final Status ACCEPTED_STATUS = Status.CREATED;
    public static final Status ACTIVE_STATUS = Status.ACTIVE;
    public static final Status FINISHED_STATUS = Status.FINISHED;
    public static final int INVALID_PAGINATION_SIZE = -1;
    public static final int DEFAULT_PAGINATION_SIZE = 1;
    public static final int INVALID_PAGINATION_PAGE = -1;
    public static final int DEFAULT_PAGINATION_PAGE = 1;
    public static final String INVALID_PAGINATION_SORTED_TYPE = "mmm";

    public RideListResponse getRideListResponse() {
        return RideListResponse.builder()
                .rideResponseList(List.of(getRideResponse(), getSecondRideResponse()))
                .build();
    }

    public List<Ride> getRideList() {
        return List.of(getRide(), getSecondRide());
    }

    public ChargeResponse getChargeResponse() {
        return ChargeResponse.builder()
                .id(PAYMENT_ID)
                .currency(CURRENCY)
                .amount(AMOUNT)
                .build();
    }

    public Ride getStartedRide() {
        return Ride.builder()
                .id(DEFAULT_ID)
                .passengerId(DEFAULT_PASSENGER_ID)
                .driverId(DEFAULT_DRIVER_ID)
                .dropOffAddress(DEFAULT_DROP_OFF_ADDRESS)
                .pickUpAddress(DEFAULT_PICK_UP_ADDRESS)
                .price(DEFAULT_PRICE)
                .instructions(DEFAULT_INSTRUCTIONS)
                .startDate(DEFAULT_START_DATE)
                .status(ACTIVE_STATUS)
                .build();
    }

    public Ride getSecondRide() {
        return Ride.builder()
                .id(SECOND_ID)
                .passengerId(SECOND_PASSENGER_ID)
                .driverId(SECOND_DRIVER_ID)
                .dropOffAddress(SECOND_DROP_OFF_ADDRESS)
                .pickUpAddress(SECOND_PICK_UP_ADDRESS)
                .price(SECOND_PRICE)
                .instructions(SECOND_INSTRUCTIONS)
                .startDate(SECOND_START_DATE)
                .status(SECOND_STATUS)
                .endDate(SECOND_END_DATE)
                .build();
    }

    public Ride getEndedRide() {
        return Ride.builder()
                .id(DEFAULT_ID)
                .passengerId(DEFAULT_PASSENGER_ID)
                .driverId(DEFAULT_DRIVER_ID)
                .dropOffAddress(DEFAULT_DROP_OFF_ADDRESS)
                .pickUpAddress(DEFAULT_PICK_UP_ADDRESS)
                .price(DEFAULT_PRICE)
                .instructions(DEFAULT_INSTRUCTIONS)
                .startDate(DEFAULT_START_DATE)
                .status(FINISHED_STATUS)
                .endDate(DEFAULT_END_DATE)
                .build();
    }

    public RideResponse getEndedRideResponse() {
        return RideResponse.builder()
                .id(DEFAULT_ID)
                .passengerId(DEFAULT_PASSENGER_ID)
                .driverId(DEFAULT_DRIVER_ID)
                .dropOffAddress(DEFAULT_DROP_OFF_ADDRESS)
                .pickUpAddress(DEFAULT_PICK_UP_ADDRESS)
                .price(DEFAULT_PRICE)
                .instructions(DEFAULT_INSTRUCTIONS)
                .startDate(DEFAULT_START_DATE)
                .status(FINISHED_STATUS)
                .endDate(DEFAULT_END_DATE)
                .build();
    }

    public RideResponse getStartedRideResponse() {
        return RideResponse.builder()
                .id(DEFAULT_ID)
                .passengerId(DEFAULT_PASSENGER_ID)
                .driverId(DEFAULT_DRIVER_ID)
                .dropOffAddress(DEFAULT_DROP_OFF_ADDRESS)
                .pickUpAddress(DEFAULT_PICK_UP_ADDRESS)
                .price(DEFAULT_PRICE)
                .instructions(DEFAULT_INSTRUCTIONS)
                .startDate(DEFAULT_START_DATE)
                .status(ACTIVE_STATUS)
                .build();
    }

    public PassengerResponse getPassengerResponse() {
        return PassengerResponse.builder()
                .id(PASSENGER_ID)
                .name(PASSENGER_NAME)
                .surname(PASSENGER_SURNAME)
                .phone(PASSENGER_PHONE)
                .email(PASSENGER_EMAIL)
                .build();
    }

    public Ride getNotSavedRide() {
        return Ride.builder()
                .passengerId(DEFAULT_PASSENGER_ID)
                .instructions(DEFAULT_INSTRUCTIONS)
                .dropOffAddress(DEFAULT_DROP_OFF_ADDRESS)
                .pickUpAddress(DEFAULT_PICK_UP_ADDRESS)
                .build();
    }

    public Ride getRideWithDriver() {
        return Ride.builder()
                .id(DEFAULT_ID)
                .passengerId(DEFAULT_PASSENGER_ID)
                .driverId(DEFAULT_DRIVER_ID)
                .dropOffAddress(DEFAULT_DROP_OFF_ADDRESS)
                .pickUpAddress(DEFAULT_PICK_UP_ADDRESS)
                .price(DEFAULT_PRICE)
                .instructions(DEFAULT_INSTRUCTIONS)
                .status(ACCEPTED_STATUS)
                .build();

    }

    public DriverForRide getDriverForRide() {
        return DriverForRide.builder()
                .rideId(DEFAULT_ID)
                .driverId(DEFAULT_DRIVER_ID)
                .build();
    }

    public Ride getRide() {
        return Ride.builder()
                .id(DEFAULT_ID)
                .passengerId(DEFAULT_PASSENGER_ID)
                .dropOffAddress(DEFAULT_DROP_OFF_ADDRESS)
                .pickUpAddress(DEFAULT_PICK_UP_ADDRESS)
                .price(DEFAULT_PRICE)
                .instructions(DEFAULT_INSTRUCTIONS)
                .status(CREATED_STATUS)
                .build();
    }

    public RideResponse getSecondRideResponse() {
        return RideResponse.builder()
                .id(SECOND_ID)
                .driverId(SECOND_DRIVER_ID)
                .passengerId(SECOND_PASSENGER_ID)
                .dropOffAddress(SECOND_DROP_OFF_ADDRESS)
                .pickUpAddress(SECOND_PICK_UP_ADDRESS)
                .price(SECOND_PRICE)
                .instructions(SECOND_INSTRUCTIONS)
                .status(SECOND_STATUS)
                .startDate(SECOND_START_DATE)
                .endDate(SECOND_END_DATE)
                .build();
    }

    public RideResponse getRideResponse() {
        return RideResponse.builder()
                .id(DEFAULT_ID)
                .driverId(DEFAULT_DRIVER_ID)
                .passengerId(DEFAULT_PASSENGER_ID)
                .dropOffAddress(DEFAULT_DROP_OFF_ADDRESS)
                .pickUpAddress(DEFAULT_PICK_UP_ADDRESS)
                .price(DEFAULT_PRICE)
                .instructions(DEFAULT_INSTRUCTIONS)
                .status(CREATED_STATUS)
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .build();
    }

    public RideRequest getRideRequest() {
        return RideRequest.builder()
                .passengerId(DEFAULT_PASSENGER_ID)
                .pickUpAddress(DEFAULT_PICK_UP_ADDRESS)
                .dropOffAddress(DEFAULT_DROP_OFF_ADDRESS)
                .instructions(DEFAULT_INSTRUCTIONS)
                .build();
    }
}
