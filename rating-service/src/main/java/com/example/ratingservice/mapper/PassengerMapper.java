package com.example.ratingservice.mapper;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.responce.PassengerRatingResponse;
import com.example.ratingservice.model.PassengerRating;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassengerMapper {
    private final ModelMapper modelMapper;

    public PassengerRatingResponse fromEntityToResponse(PassengerRating passengerRating){
        return modelMapper.map(passengerRating, PassengerRatingResponse.class);
    }
    public PassengerRating fromRequestToEntity(CreateRequest request){
        return modelMapper.map(request,PassengerRating.class);
    }





}
