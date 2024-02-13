package com.example.end2end.dto.response;

import com.example.end2end.enums.Status;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
@Setter
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
