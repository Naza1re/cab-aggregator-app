package com.example.ratingservice.util;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.DriverRatingResponse;
import com.example.ratingservice.dto.responce.PassengerRatingResponse;

public class IntegrationTestUtil {
    public static final Long DEFAULT_ID = 1L;
    public static final Long CREATE_ID = 4L;
    public static final Long DEFAULT_DRIVER_ID = 4L;
    public static final double DEFAULT_RATE = 5.0;
    public static final double NEW_RATE = 1.0;
    public static final double UPDATE_RATE = 3.0;
    public static final Long ID_NOT_FOUND = 100L;
    public static final String PATH_ID_DRIVER = "/api/v1/rating/driver/{id}";
    public static final String PATH_ID_PASSENGER = "/api/v1/rating/passenger/{id}";
    public static final String PATH_DEFAULT_DRIVER = "/api/v1/rating/driver";
    public static final String PATH_DEFAULT_PASSENGER = "/api/v1/rating/passenger";
    public static final String PATH_PARAM_ID = "id";


    public static UpdateRequest getUpdateRequest() {
        return UpdateRequest.builder()
                .rate(NEW_RATE)
                .id(DEFAULT_ID)
                .build();
    }

    public static DriverRatingResponse genUpdateDriverRatingResponse() {
        return DriverRatingResponse.builder()
                .id(DEFAULT_ID)
                .rate(UPDATE_RATE)
                .driver(DEFAULT_ID)
                .build();
    }

    public static PassengerRatingResponse genUpdatePassengerRatingResponse() {
        return PassengerRatingResponse.builder()
                .id(DEFAULT_ID)
                .rate(UPDATE_RATE)
                .passenger(DEFAULT_ID)
                .build();
    }

    public static CreateRequest getCreateRequest() {
        return CreateRequest.builder()
                .id(DEFAULT_DRIVER_ID)
                .build();
    }

    public static DriverRatingResponse getCreateDriverRatingResponse() {
        return DriverRatingResponse.builder()
                .id(CREATE_ID)
                .driver(DEFAULT_DRIVER_ID)
                .rate(DEFAULT_RATE)
                .build();
    }

    public static PassengerRatingResponse getCreatePassengerRatingResponse() {
        return PassengerRatingResponse.builder()
                .id(CREATE_ID)
                .passenger(DEFAULT_DRIVER_ID)
                .rate(DEFAULT_RATE)
                .build();
    }


}
