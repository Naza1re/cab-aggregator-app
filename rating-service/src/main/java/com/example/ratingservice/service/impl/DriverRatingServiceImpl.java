package com.example.ratingservice.service.impl;

import brave.Span;
import brave.Tracer;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.ratingservice.util.ConstantsMessages.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DriverRatingServiceImpl implements DriverRatingService {

    @Autowired
    private Tracer tracer;
    private final DriverRatingRepository driverRatingRepository;

    private final DriverMapper driverMapper;

    @Override
    public DriverRatingResponse getDriverById(Long driverId) {
        DriverRating driverRating = getOrThrowByDriverId(driverId);
        log.info(String.format(GET_DRIVER_BY_ID_LOG_MESSAGE, driverRating));
        return driverMapper.fromEntityToResponse(driverRating);
    }

    @Override
    public DriverRatingResponse createDriver(CreateRequest request) {
        checkDriverRatingExist(request.getId());
        Span span = tracer.currentSpan();
        log.info("Trace ID {}", span.context().traceId());
        log.info("Span ID {}", span.context().spanId());
        DriverRating driverRating = new DriverRating();
        driverRating.setDriver(request.getId());
        driverRating.setRate(Constants.DEFAULT_RATING);

        DriverRating savedDriverRating = driverRatingRepository.save(driverRating);
        log.info(String.format(CREATE_DRIVER_WITH_ID, savedDriverRating.getId()));
        return driverMapper.fromEntityToResponse(savedDriverRating);
    }

    @Override
    public DriverRatingResponse updateDriverRate(UpdateRequest updateRequest) {
        DriverRating driverRating = getOrThrowByDriverId(updateRequest.getId());

        double newRate = (driverRating.getRate() + updateRequest.getRate()) / 2;
        driverRating.setRate(newRate);
        driverRatingRepository.save(driverRating);
        log.info(String.format(UPDATE_DRIVER_WITH_ID, updateRequest.getId()));
        return driverMapper.fromEntityToResponse(driverRating);
    }

    @Override
    public DriverRatingResponse deleteDriverRecord(Long driverId) {
        DriverRating driverRating = getOrThrowByDriverId(driverId);

        driverRatingRepository.delete(driverRating);
        log.info(String.format(DELETE_DRIVER_WITH_ID, driverId));
        return driverMapper.fromEntityToResponse(driverRating);
    }

    private void checkDriverRatingExist(Long driverId) {
        if (driverRatingRepository.existsByDriver(driverId)) {
            log.info(String.format(DRIVER_RECORD_EXIST, driverId));
            throw new DriverRatingAlreadyExistException(String.format(ExceptionMessages.DRIVER_RATING_ALREADY_EXIST, driverId));
        }
    }

    @Override
    public DriverRatingListResponse getAllDriversRecords() {
        List<DriverRatingResponse> driverRatings = driverRatingRepository.findAll()
                .stream()
                .map(driverMapper::fromEntityToResponse)
                .toList();
        log.info(GET_DRIVER_RECORD_LIST);
        return new DriverRatingListResponse(driverRatings);
    }

    private DriverRating getOrThrowByDriverId(Long id) {
        return driverRatingRepository.findDriverRatingByDriver(id)
                .orElseThrow(() -> new DriverRatingNotFoundException(String.format(ExceptionMessages.DRIVER_RATING_NOT_FOUND, id)));
    }
}
