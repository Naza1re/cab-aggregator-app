package com.example.passengerservice.service;

import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.response.PassengerListResponse;
import com.example.passengerservice.dto.response.PassengerPageResponse;
import com.example.passengerservice.dto.response.PassengerResponse;

public interface PassengerService {

    PassengerResponse getPassengerById(Long id);

    PassengerListResponse getAllPassengers();

    PassengerResponse createPassenger(PassengerRequest passengerRequest);

    PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest);

    PassengerResponse deletePassenger(Long id);

    PassengerPageResponse getPassengerPage(int page, int size, String orderBy);

}