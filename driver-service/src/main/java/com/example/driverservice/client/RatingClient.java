package com.example.driverservice.client;

import com.example.driverservice.config.FeignClientConfiguration;
import com.example.driverservice.dto.request.RatingRequest;
import com.example.driverservice.dto.response.DriverRatingListResponse;
import com.example.driverservice.dto.response.DriverRatingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${feign.client.rating.name}", url = "${feign.client.rating.url}",
        path = "${feign.client.rating.path}", configuration = FeignClientConfiguration.class)
public interface RatingClient {
    @GetMapping("/driver/list")
    DriverRatingListResponse getDriversRateList();

    @PostMapping("/driver")
    DriverRatingResponse createDriverRecord(@RequestBody RatingRequest request);

}

