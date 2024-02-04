package com.example.rideservice.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@ToString
public class RideListResponse {
    private List<RideResponse> rideResponseList;

}
