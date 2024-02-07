package com.example.rideservice.dto.response;

import com.example.rideservice.model.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class RideResponse {
    private Long id;

    private Long driverId;

    private Long passengerId;

    private String pickUpAddress;

    private String dropOffAddress;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal price;

    private Status status;
    private String instructions;

}
