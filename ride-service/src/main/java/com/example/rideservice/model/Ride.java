package com.example.rideservice.model;

import com.example.rideservice.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
