package com.example.rideservice.dto.response;

import lombok.Builder;

import java.util.List;
@Builder
public class RidePageResponse {
    private List<RideResponse> rideList;
    private long totalElements;
    private int totalPages;

}
