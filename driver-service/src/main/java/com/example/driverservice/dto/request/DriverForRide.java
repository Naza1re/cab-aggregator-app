package com.example.driverservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverForRide {
    private Long driverId;
    private Long rideId;
}
