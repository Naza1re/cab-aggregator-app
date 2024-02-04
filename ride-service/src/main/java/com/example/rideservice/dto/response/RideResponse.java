package com.example.rideservice.dto.response;

import com.example.rideservice.model.enums.Status;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
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
