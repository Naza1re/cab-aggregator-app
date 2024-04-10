package com.example.passengerservice.service;

import com.example.passengerservice.dto.request.RatingRequest;

public interface RatingService {

    void createPassengerRecord(RatingRequest request);

    void deletePassengerRecord(Long id);
}
