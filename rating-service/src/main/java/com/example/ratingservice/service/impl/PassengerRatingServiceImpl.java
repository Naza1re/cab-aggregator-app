package com.example.ratingservice.service.impl;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.PassengerListResponse;
import com.example.ratingservice.dto.responce.PassengerRatingResponse;
import com.example.ratingservice.exception.PassengerRatingAlreadyExistException;
import com.example.ratingservice.exception.PassengerRatingNotFoundException;
import com.example.ratingservice.mapper.PassengerMapper;
import com.example.ratingservice.model.PassengerRating;
import com.example.ratingservice.repository.PassengerRatingRepository;
import com.example.ratingservice.service.PassengerRatingService;
import com.example.ratingservice.util.Constants;
import com.example.ratingservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerRatingServiceImpl implements PassengerRatingService {

    private final PassengerRatingRepository passengerRatingRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerRatingResponse getPassengerRecordById(Long passengerId) {

        PassengerRating passengerRating = getOrThrowByPassengerId(passengerId);
        return passengerMapper.fromEntityToResponse(passengerRating);
    }

    @Override
    public PassengerRatingResponse createPassenger(CreateRequest createRequest) {
        checkPassengerRatingExist(createRequest.getId());

        PassengerRating passengerRating = new PassengerRating();
        passengerRating.setPassenger(createRequest.getId());
        passengerRating.setRate(Constants.DEFAULT_RATING);
        passengerRatingRepository.save(passengerRating);

        return passengerMapper.fromEntityToResponse(passengerRating);

    }

    @Override
    public PassengerRatingResponse updatePassengerRating(UpdateRequest updateRequest) {
        PassengerRating passengerRating = getOrThrowByPassengerId(updateRequest.getId());

        double newRate = (passengerRating.getRate() + updateRequest.getRate()) / 2;
        passengerRating.setRate(newRate);
        passengerRatingRepository.save(passengerRating);
        return passengerMapper.fromEntityToResponse(passengerRating);
    }

    @Override
    public PassengerRatingResponse deletePassengerRecord(Long passengerId) {
        PassengerRating passengerRating = getOrThrowByPassengerId(passengerId);

        passengerRatingRepository.delete(passengerRating);
        return passengerMapper.fromEntityToResponse(passengerRating);
    }

    @Override
    public PassengerListResponse getAllPassengersRecords() {
        List<PassengerRatingResponse> passengerRatings = passengerRatingRepository.findAll()
                .stream()
                .map(passengerMapper::fromEntityToResponse)
                .toList();
        return new PassengerListResponse(passengerRatings);
    }

    private void checkPassengerRatingExist(Long passengerId) {
        if (passengerRatingRepository.existsByPassenger(passengerId)) {
            throw new PassengerRatingAlreadyExistException(String.format(ExceptionMessages.PASSENGER_RATING_ALREADY_EXIST, passengerId));
        }
    }

    private PassengerRating getOrThrowByPassengerId(Long passengerId) {
        return passengerRatingRepository.findPassengerRatingByPassenger(passengerId)
                .orElseThrow(() -> new PassengerRatingNotFoundException(String.format(ExceptionMessages.PASSENGER_RATING_NOT_FOUND, passengerId)));
    }


}

