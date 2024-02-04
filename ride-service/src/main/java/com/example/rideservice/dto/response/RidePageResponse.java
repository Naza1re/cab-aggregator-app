package com.example.rideservice.dto.response;

import lombok.*;

import java.util.List;
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RidePageResponse {
    private List<RideResponse> rideList;
    private long totalElements;
    private int totalPages;

}
