package com.example.passengerservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PassengerListResponse {

    private List<PassengerResponse> listOfPassengers;

}
