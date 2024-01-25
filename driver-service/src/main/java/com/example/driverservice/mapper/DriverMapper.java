package com.example.driverservice.mapper;

import com.example.driverservice.dto.request.DriverRequest;
import com.example.driverservice.dto.response.DriverResponse;
import com.example.driverservice.model.Driver;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverMapper {

    private final ModelMapper modelMapper;


    public DriverResponse fromEntityToResponse(Driver driver) {
        return modelMapper.map(driver, DriverResponse.class);
    }

    public Driver fromRequestToEntity(DriverRequest driverRequest) {
        return modelMapper.map(driverRequest, Driver.class);
    }
}
