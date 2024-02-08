package com.example.rideservice.dto.response;

import lombok.*;

import java.util.List;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RidePageResponse {
    private List<RideResponse> rideList;
    private long totalElements;
    private int totalPages;

}
