package com.example.driverservice.service;

import com.example.driverservice.dto.request.CarOwnerRequest;
import com.example.driverservice.dto.response.CarResponse;

public interface CarParkService {

    CarResponse getCarById(Long id);

    CarResponse setOwner(CarOwnerRequest carOwnerRequest);

}
