package com.example.rideservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RideListResponse {
    private List<RideResponse> rideResponseList;

}
