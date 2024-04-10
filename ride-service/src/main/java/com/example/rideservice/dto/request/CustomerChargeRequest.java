package com.example.rideservice.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CustomerChargeRequest {
    private Long passengerId;
    private String currency;
    private BigDecimal amount;
}
