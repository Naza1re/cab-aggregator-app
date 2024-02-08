package com.example.passengerservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerRatingResponse {
    private Long id;
    private Long passenger;
    private double rate;
}
