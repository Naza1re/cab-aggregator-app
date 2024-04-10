package com.example.driverservice.service.impl;

import com.example.driverservice.client.RatingFeignClient;
import com.example.driverservice.dto.request.RatingRequest;
import com.example.driverservice.dto.response.DriverRatingListResponse;
import com.example.driverservice.exception.FeignClientException;
import com.example.driverservice.exception.NotFoundException;
import com.example.driverservice.exception.ServiceUnAvailableException;
import com.example.driverservice.service.RatingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.driverservice.util.ExceptionMessages.RATING_SERVICE_IS_NOT_AVAILABLE;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingFeignClient ratingFeignClient;

    @Override
    @CircuitBreaker(name = "rating", fallbackMethod = "fallBackRatingService")
    public void createDriverRecord(RatingRequest request) {
        ratingFeignClient.createDriverRecord(request);
    }

    @Override
    @CircuitBreaker(name = "rating", fallbackMethod = "fallBackRatingServiceGettingList")
    public DriverRatingListResponse getDriversRateList() {

        return ratingFeignClient.getDriversRateList();
    }

    @Override
    @CircuitBreaker(name = "rating", fallbackMethod = "fallBackRatingService")
    public void deleteDriverRating(Long id) {
        ratingFeignClient.deleteDriverRating(id);
    }

    private void fallBackRatingService(Exception ex) {
        log.error("Rating service is not available for getting list. Fallback method called.");
        throw new ServiceUnAvailableException(RATING_SERVICE_IS_NOT_AVAILABLE);
    }

    private DriverRatingListResponse fallBackRatingServiceGettingList(FeignClientException ex) {
        log.error("Something went wrong during getting list. Fallback method called.");
        return new DriverRatingListResponse();
    }

    private void fallBackRatingService(FeignClientException ex) {
        log.error("Something went wrong. Fallback method called.");
        throw new FeignClientException(ex.getMessage());
    }

    private void fallBackRatingService(NotFoundException ex) {
        log.error("NotFoundException. Fall back method was called.");
        throw new NotFoundException(ex.getMessage());
    }

    private DriverRatingListResponse fallBackRatingServiceGettingList(Exception ex) {
        log.error("Rating service is not available for getting list. Fallback method called.");
        return new DriverRatingListResponse();
    }
}
