package com.example.end2end.client;

import com.example.end2end.config.FeignClientConfiguration;
import com.example.end2end.dto.request.RideRequest;
import com.example.end2end.dto.response.RideResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "${feign.client.ride.name}", url = "${feign.client.ride.url}",
        path = "${feign.client.ride.path}", configuration = FeignClientConfiguration.class)
public interface RideFeignClient {
    @PostMapping
    RideResponse findRide(@RequestBody RideRequest rideRequest);

    @PutMapping("/{rideId}/start")
    RideResponse startRide(@PathVariable Long rideId);

    @GetMapping("/{rideId}")
    RideResponse getRideById(@PathVariable Long rideId);

}
