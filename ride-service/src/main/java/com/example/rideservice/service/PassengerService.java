package com.example.rideservice.service;

import com.example.rideservice.dto.response.PassengerResponse;

public interface PassengerService {

    PassengerResponse getPassenger(Long id);
}
