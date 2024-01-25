package com.example.rideservice.model;

import com.example.rideservice.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ride")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long driverId;
    private Long passengerId;
    private String pickUpAddress;
    private String dropOffAddress;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String instructions;
}
