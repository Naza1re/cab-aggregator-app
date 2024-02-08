package com.example.ratingservice.util;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.PassengerRatingResponse;
import com.example.ratingservice.model.PassengerRating;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PassengerRatingUtilTest {

    public static final Long DEFAULT_ID = 1L;
    public static final Long NOT_EXIST_ID = 100L;
    public static final Long DEFAULT_PASSENGER_ID = 1L;
    public static final double DEFAULT_RATE = 5;

    public UpdateRequest getUpdateRequest() {
        return UpdateRequest.builder()
                .id(DEFAULT_ID)
                .rate(DEFAULT_RATE)
                .build();
    }

    public PassengerRating getSavedPassengerRating() {
        return PassengerRating.builder()
                .id(DEFAULT_ID)
                .passenger(DEFAULT_PASSENGER_ID)
                .rate(DEFAULT_RATE)
                .build();
    }

    public PassengerRating getDefaultPassengerRating() {
        return PassengerRating.builder()
                .passenger(DEFAULT_PASSENGER_ID)
                .rate(DEFAULT_RATE)
                .build();
    }

    public PassengerRatingResponse getDefaultPassengerRatingResponse() {
        return PassengerRatingResponse.builder()
                .id(DEFAULT_ID)
                .passenger(DEFAULT_PASSENGER_ID)
                .rate(DEFAULT_RATE)
                .build();
    }

    public CreateRequest getCreateRequest() {
        return CreateRequest.builder()
                .id(DEFAULT_ID)
                .build();
    }

}
