package com.example.driverservice.service;

import com.example.driverservice.dto.request.RatingRequest;
import com.example.driverservice.dto.response.DriverRatingListResponse;

public interface RatingService {

    DriverRatingListResponse getDriversRateList();

    void createDriverRecord(RatingRequest request);

    void deleteDriverRating(Long id);

}
