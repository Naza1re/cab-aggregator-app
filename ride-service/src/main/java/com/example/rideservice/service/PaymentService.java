package com.example.rideservice.service;

import com.example.rideservice.dto.request.CustomerChargeRequest;

public interface PaymentService {
    void chargeFromCustomer(CustomerChargeRequest request);
}
