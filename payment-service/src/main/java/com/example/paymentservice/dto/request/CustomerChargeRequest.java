package com.example.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerChargeRequest {
    @NotNull(message = "{passenger.id.not.blank}")
    private Long passengerId;
    @Size(min = 3, max = 3, message = "{currency.size}")
    @NotBlank(message = "{currency.not.blank}")
    private String currency;
    @NotNull(message = "{amount.not.blank}")
    private Long amount;
}
