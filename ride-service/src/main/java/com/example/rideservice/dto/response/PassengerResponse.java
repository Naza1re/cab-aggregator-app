package com.example.rideservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PassengerResponse {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
}
