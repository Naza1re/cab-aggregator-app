package com.example.rideservice.service;

import com.example.rideservice.dto.request.PriceCalculateRequest;
import com.example.rideservice.dto.response.PriceCalculateResponse;

public interface PriceService {

    PriceCalculateResponse getCalculatePrice(PriceCalculateRequest request);
}
