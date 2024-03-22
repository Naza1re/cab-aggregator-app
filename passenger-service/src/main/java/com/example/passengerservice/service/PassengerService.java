package com.example.passengerservice.service;

import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.response.PassengerListResponse;
import com.example.passengerservice.dto.response.PassengerPageResponse;
import com.example.passengerservice.dto.response.PassengerResponse;
import com.example.passengerservice.security.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;

public interface PassengerService {

    PassengerResponse getPassengerById(Long id);

    PassengerListResponse getAllPassengers();

    PassengerResponse createPassenger(PassengerRequest passengerRequest);

    PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest);

    PassengerResponse deletePassenger(Long id);

    PassengerPageResponse getPassengerPage(int page, int size, String orderBy);
    public User extractUserInfo(Jwt jwt);
    public PassengerRequest getPassengerRequestFromOauth2User(OAuth2User oAuth2User);

}