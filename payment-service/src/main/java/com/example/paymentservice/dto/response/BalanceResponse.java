package com.example.paymentservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BalanceResponse {
    Long amount;
    String currency;
}
