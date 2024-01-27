package com.example.passengerservice.mapper;

import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.response.PassengerResponse;
import com.example.passengerservice.model.Passenger;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PassengerMapper {

    private final ModelMapper modelMapper;


    public PassengerResponse fromEntityToResponse(Passenger passenger) {
        return modelMapper.map(passenger, PassengerResponse.class);
    }

    public Passenger fromRequestToEntity(PassengerRequest passengerRequest) {
        return modelMapper.map(passengerRequest, Passenger.class);
    }
}
