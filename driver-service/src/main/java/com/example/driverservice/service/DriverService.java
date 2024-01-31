package com.example.driverservice.service;

import com.example.driverservice.dto.request.DriverRequest;
import com.example.driverservice.dto.response.DriverListResponse;
import com.example.driverservice.dto.response.DriverPageResponse;
import com.example.driverservice.dto.response.DriverResponse;

public interface DriverService {

    DriverResponse getDriverById(Long id);

    DriverListResponse getListOfDrivers();

    DriverResponse updateDriver(Long id, DriverRequest driverRequest);

    DriverResponse createDriver(DriverRequest driverRequest);

    DriverResponse deleteDriver(Long id);

    DriverListResponse getAvailableDrivers();

    DriverPageResponse getDriverPage(int page, int size, String orderBy);

    DriverResponse changeStatus(Long driverId);

    void handleDriver(Long rideId);
}