package com.example.passengerservice.dto.response;

import lombok.*;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class PassengerListResponse {

    private List<PassengerResponse> listOfPassengers;

}
