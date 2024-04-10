package com.example.rideservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ChargeResponse {
    String id;
    String currency;
    BigDecimal amount;
}