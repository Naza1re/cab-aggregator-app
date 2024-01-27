package com.example.rideservice.service;

import com.example.rideservice.dto.request.RideRequest;
import com.example.rideservice.dto.response.DriverForRide;
import com.example.rideservice.dto.response.RideListResponse;
import com.example.rideservice.dto.response.RidePageResponse;
import com.example.rideservice.dto.response.RideResponse;

public interface RideService {

    RideResponse startRide(Long ride_id);

    RideResponse endRide(Long rideId);

    RideResponse getRideById(Long id);

    RideListResponse getListOfRidesByPassengerId(Long passengerId);

    RideListResponse getListOfRidesByDriverId(Long driverId);

    RidePageResponse getRidePage(int page, int size, String orderBy);

    RideResponse findRide(RideRequest rideRequest);

    void setDriver(DriverForRide driver);

    void findRideForAvailableDriver();
}