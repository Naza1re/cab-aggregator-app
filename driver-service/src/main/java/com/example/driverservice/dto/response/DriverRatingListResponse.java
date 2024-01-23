package com.example.driverservice.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DriverRatingListResponse {
    private List<DriverRatingResponse> driverRatingResponseList;
}
