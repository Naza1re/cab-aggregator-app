package com.example.driverservice.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class DriverListResponse {

    private List<DriverResponse> driverResponseList;

}
