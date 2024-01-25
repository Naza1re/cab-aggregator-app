package com.example.passengerservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class PassengerPageResponse {
    private List<PassengerResponse> passengerList;
    private long totalElements;
    private int totalPages;

}