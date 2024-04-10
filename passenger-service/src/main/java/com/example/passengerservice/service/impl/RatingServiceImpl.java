package com.example.passengerservice.service.impl;

import com.example.passengerservice.client.RatingFeignClient;
import com.example.passengerservice.dto.request.RatingRequest;
import com.example.passengerservice.exception.FeignClientException;
import com.example.passengerservice.exception.NotFoundException;
import com.example.passengerservice.exception.ServiceUnAvailableException;
import com.example.passengerservice.service.RatingService;
import com.example.passengerservice.util.ExceptionMessages;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingFeignClient ratingFeignClient;

    @Override
    @CircuitBreaker(name = "rating", fallbackMethod = "fallbackRatingService")
    public void createPassengerRecord(RatingRequest request) {
        ratingFeignClient.createPassengerRecord(request);
    }

    @Override
    @CircuitBreaker(name = "rating", fallbackMethod = "fallbackRatingService")
    public void deletePassengerRecord(Long id) {
        ratingFeignClient.deletePassengerRecord(id);
    }

    private void fallbackRatingService(NotFoundException ex) {
        log.info("Ratting record not found. Fallback method was called");
        throw new NotFoundException(ex.getMessage());
    }

    private void fallbackRatingService(FeignClientException ex) {
        log.info("Something went wrong. Fallback method was called");
        throw new FeignClientException(ex.getMessage());
    }

    private void fallbackRatingService(Exception ex) {
        log.info("Rating service is not available. Fallback method was called");
        throw new ServiceUnAvailableException(ExceptionMessages.RATING_SERVICE_IS_NOT_AVAILABLE);
    }
}
