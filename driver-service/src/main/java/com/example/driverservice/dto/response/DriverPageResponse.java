package com.example.driverservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverPageResponse {

    private List<DriverResponse> driverList;
    private long totalElements;
    private int totalPages;

}
