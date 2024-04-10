package com.example.rideservice.service;

import com.example.rideservice.dto.response.PromoCodeResponse;

public interface PromoCodeService {
    PromoCodeResponse getPromCode(String value);
}
