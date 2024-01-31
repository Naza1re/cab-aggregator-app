package com.example.driverservice.client;

import com.example.driverservice.config.FeignClientConfiguration;
import com.example.driverservice.dto.request.RatingRequest;
import com.example.driverservice.dto.response.DriverRatingListResponse;
import com.example.driverservice.dto.response.DriverRatingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "${feign.client.rating.name}", url = "${feign.client.rating.url}",
        path = "${feign.client.rating.path}", configuration = FeignClientConfiguration.class)
public interface RatingFeignClient {
    @GetMapping("/driver/list")
    DriverRatingListResponse getDriversRateList();

    @PostMapping("/driver")
    DriverRatingResponse createDriverRecord(@RequestBody RatingRequest request);

    @DeleteMapping("/driver/{id}")
    DriverRatingResponse deleteDriverRating(@PathVariable Long id);

}

