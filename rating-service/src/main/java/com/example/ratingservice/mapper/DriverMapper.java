package com.example.ratingservice.mapper;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.responce.DriverRatingResponse;
import com.example.ratingservice.model.DriverRating;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DriverMapper {

    private final ModelMapper modelMapper;

    public DriverRatingResponse fromEntityToResponse(DriverRating driverRating){
        return modelMapper.map(driverRating, DriverRatingResponse.class);
    }
    public DriverRating fromRequestToEntity(CreateRequest driverRequest){
        return modelMapper.map(driverRequest,DriverRating.class);
    }
}
