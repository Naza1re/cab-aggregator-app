package com.example.end2end.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class RideRequest {
    private Long passengerId;
    private String pickUpAddress;
    private String dropOffAddress;
    private String instructions;

}
