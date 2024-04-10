package com.example.ratingservice.util;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.DriverRatingResponse;
import com.example.ratingservice.model.DriverRating;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DriverRatingUtilTest {

    public static final Long DEFAULT_ID = 1L;
    public static final Long NOT_EXIST_ID = 100L;
    public static final Long DEFAULT_DRIVER_ID = 1L;
    public static final double DEFAULT_RATE = 5;

    public UpdateRequest getUpdateRequest() {
        return UpdateRequest.builder()
                .id(DEFAULT_ID)
                .rate(DEFAULT_RATE)
                .build();
    }

    public DriverRating getSavedDriverRating() {
        return DriverRating.builder()
                .id(DEFAULT_ID)
                .driver(DEFAULT_DRIVER_ID)
                .rate(DEFAULT_RATE)
                .build();
    }

    public DriverRating getDefaultDriverRating() {
        return DriverRating.builder()
                .driver(DEFAULT_DRIVER_ID)
                .rate(DEFAULT_RATE)
                .build();
    }

    public DriverRatingResponse getDefaultDriverRatingResponse() {
        return DriverRatingResponse.builder()
                .id(DEFAULT_ID)
                .driver(DEFAULT_DRIVER_ID)
                .rate(DEFAULT_RATE)
                .build();
    }

    public CreateRequest getCreateRequest() {
        return CreateRequest.builder()
                .id(DEFAULT_ID)
                .build();
    }

}