package com.example.ratingservice.service;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.DriverListResponse;
import com.example.ratingservice.dto.responce.DriverRatingResponse;

public interface DriverRatingService {

    DriverRatingResponse getDriverById(Long driverId);

    DriverRatingResponse createDriver(CreateRequest createRequest);

    DriverRatingResponse updateDriverRate(UpdateRequest updateRequest);

    DriverRatingResponse deleteDriverRecord(Long driverId);

    DriverListResponse getAllDriversRecords();
}