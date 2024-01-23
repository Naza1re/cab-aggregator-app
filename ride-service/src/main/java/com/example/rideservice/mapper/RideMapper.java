package com.example.rideservice.mapper;

import com.example.rideservice.dto.request.RideRequest;
import com.example.rideservice.dto.response.RideResponse;
import com.example.rideservice.model.Ride;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RideMapper {

    private final ModelMapper modelMapper;

    public Ride fromRequestToEntity(RideRequest request) {
        return modelMapper.map(request, Ride.class);
    }

    public RideResponse fromEntityToResponse(Ride ride) {
        return modelMapper.map(ride, RideResponse.class);
    }
}
