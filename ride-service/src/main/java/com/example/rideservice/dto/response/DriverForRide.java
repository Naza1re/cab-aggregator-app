package com.example.rideservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DriverForRide {
    private Long driverId;
    private Long rideId;
}
