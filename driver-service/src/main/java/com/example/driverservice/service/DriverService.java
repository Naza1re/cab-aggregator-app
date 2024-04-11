package com.example.driverservice.service;

import com.example.driverservice.dto.request.DriverRequest;
import com.example.driverservice.dto.response.DriverListResponse;
import com.example.driverservice.dto.response.DriverPageResponse;
import com.example.driverservice.dto.response.DriverResponse;
import com.example.driverservice.security.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;

public interface DriverService {

    DriverResponse getDriverById(Long id);

    DriverListResponse getListOfDrivers();

    DriverResponse updateDriver(Long id, DriverRequest driverRequest);

    DriverResponse createDriver(DriverRequest driverRequest);

    DriverResponse deleteDriver(Long id);

    DriverListResponse getAvailableDrivers();

    DriverPageResponse getDriverPage(int page, int size, String orderBy);

    DriverResponse changeStatus(Long driverId);

    void handleDriver(Long driverId);

    DriverRequest getDriverRequestFromOauth2User(OAuth2User user);

    User extractUserInfo(Jwt jwt);

    DriverResponse createDriverWithoutCar(DriverRequest request, Long carId);
}