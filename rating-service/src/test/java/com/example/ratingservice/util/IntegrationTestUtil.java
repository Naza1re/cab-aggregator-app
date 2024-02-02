package com.example.ratingservice.util;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.DriverRatingResponse;
import lombok.experimental.UtilityClass;


@UtilityClass
public class IntegrationTestUtil {
    public static final Long DEFAULT_ID = 1L;
    public static final Long CREATE_ID = 4L;
    public static final Long DEFAULT_DRIVER_ID = 4L;
    public static final double DEFAULT_RATE = 5.0;
    public static final double NEW_RATE = 1.0;
    public static final double UPDATE_RATE = 3.0;
    public static final Long ID_NOT_FOUND = 100L;
    public static final String PATH_ID = "/api/v1/rating/driver/{id}";
    public static final String PATH_DEFAULT = "/api/v1/rating/driver";
    public static final String PATH_PARAM_ID = "id";


    public UpdateRequest getUpdateRequest() {
        return UpdateRequest.builder()
                .rate(NEW_RATE)
                .id(DEFAULT_ID)
                .build();
    }

    public DriverRatingResponse genUpdateDriverRatingResponse() {
        return DriverRatingResponse.builder()
                .id(DEFAULT_ID)
                .rate(UPDATE_RATE)
                .driver(DEFAULT_ID)
                .build();
    }

    public CreateRequest getCreateRequest() {
        return CreateRequest.builder()
                .id(DEFAULT_DRIVER_ID)
                .build();
    }

    public DriverRatingResponse getCreateDriverRatingResponse() {
        return DriverRatingResponse.builder()
                .id(CREATE_ID)
                .driver(DEFAULT_DRIVER_ID)
                .rate(DEFAULT_RATE)
                .build();
    }


}
