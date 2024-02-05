package com.example.rideservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
public class RidePageResponse {
    private List<RideResponse> rideList;
    private long totalElements;
    private int totalPages;

}
