package com.example.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeRequest {
    @NotNull(message = "{amount.not.blank}")
    private Long amount;
    @Size(min = 3, max = 3, message = "{currency.size}")
    @NotBlank(message = "{currency.not.blank}")
    private String currency;
    @NotBlank(message = "{token.not.blank}")
    private String token;
}
