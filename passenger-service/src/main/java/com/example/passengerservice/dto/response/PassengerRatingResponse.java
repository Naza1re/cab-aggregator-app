package com.example.passengerservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassengerRatingResponse {
    private Long id;
    private Long passenger;
    private double rate;
}
