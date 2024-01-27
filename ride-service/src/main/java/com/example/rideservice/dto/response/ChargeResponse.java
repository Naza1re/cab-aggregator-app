package com.example.rideservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChargeResponse {
    String id;
    String currency;
    long amount;
}