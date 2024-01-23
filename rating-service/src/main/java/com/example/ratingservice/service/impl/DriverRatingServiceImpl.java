package com.example.ratingservice.service.impl;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.DriverRatingListResponse;
import com.example.ratingservice.dto.responce.DriverRatingResponse;
import com.example.ratingservice.exception.DriverRatingAlreadyExistException;
import com.example.ratingservice.exception.DriverRatingNotFoundException;
import com.example.ratingservice.mapper.DriverMapper;
import com.example.ratingservice.model.DriverRating;
import com.example.ratingservice.repository.DriverRatingRepository;
import com.example.ratingservice.service.DriverRatingService;
import com.example.ratingservice.util.Constants;
import com.example.ratingservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverRatingServiceImpl implements DriverRatingService {

    private final DriverRatingRepository driverRatingRepository;

    private final DriverMapper driverMapper;

    public DriverRatingResponse getDriverById(Long driverId) {
        DriverRating driverRating = getOrThrowByDriverId(driverId);
        return driverMapper.fromEntityToResponse(driverRating);
    }

    public DriverRatingResponse createDriver(CreateRequest request) {

        checkDriverRatingExist(request.getId());

        DriverRating driverRating = new DriverRating();
        driverRating.setDriver(request.getId());
        driverRating.setRate(Constants.DEFAULT_RATING);

        driverRatingRepository.save(driverRating);
        return driverMapper.fromEntityToResponse(driverRating);
    }

    public DriverRatingResponse updateDriverRate(UpdateRequest updateRequest) {
        DriverRating driverRating = getOrThrowByDriverId(updateRequest.getId());

        double newRate = (driverRating.getRate() + updateRequest.getRate()) / 2;
        driverRating.setRate(newRate);
        driverRatingRepository.save(driverRating);
        return driverMapper.fromEntityToResponse(driverRating);
    }

    public DriverRatingResponse deleteDriverRecord(Long driverId) {
        DriverRating driverRating = getOrThrowByDriverId(driverId);

        driverRatingRepository.delete(driverRating);
        return driverMapper.fromEntityToResponse(driverRating);
    }

    public void checkDriverRatingExist(Long driverId) {
        if (driverRatingRepository.existsByDriver(driverId)) {
            throw new DriverRatingAlreadyExistException(String.format(ExceptionMessages.DRIVER_RATING_ALREADY_EXIST, driverId));
        }
    }

    public DriverRatingListResponse getAllDriversRecords() {
        List<DriverRatingResponse> driverRatings = driverRatingRepository.findAll()
                .stream()
                .map(driverMapper::fromEntityToResponse)
                .toList();
        return new DriverRatingListResponse(driverRatings);
    }

    public DriverRating getOrThrowByDriverId(Long id) {
        return driverRatingRepository.findDriverRatingByDriver(id)
                .orElseThrow(() -> new DriverRatingNotFoundException(String.format(ExceptionMessages.DRIVER_RATING_NOT_FOUND, id)));
    }
}
