package com.example.driverservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarResponse {

    private Long id;
    private String model;
    private String mark;
    private String year;
    private String type;
    private String color;
    private String number;
    private String owner;

}
