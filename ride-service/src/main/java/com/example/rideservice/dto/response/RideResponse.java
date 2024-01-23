package com.example.rideservice.dto.response;

import com.example.rideservice.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideResponse {
    private Long id;

    private Long driverId;

    private Long passengerId;

    private String pickUpAddress;

    private String dropOffAddress;

    private LocalDate startDate;

    private LocalDate endDate;

    private double price;

    private Status status;

}
