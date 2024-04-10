package com.example.ratingservice.service;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.PassengerListResponse;
import com.example.ratingservice.dto.responce.PassengerRatingResponse;

public interface PassengerRatingService {

    PassengerRatingResponse getPassengerRecordById(Long passengerId);

    PassengerRatingResponse createPassenger(CreateRequest createRequest);

    PassengerRatingResponse updatePassengerRating(UpdateRequest updateRequest);

    PassengerRatingResponse deletePassengerRecord(Long passengerId);

    PassengerListResponse getAllPassengersRecords();
}