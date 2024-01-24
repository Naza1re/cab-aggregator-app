package com.example.passengerservice.client;

import com.example.passengerservice.config.FeignClientConfiguration;
import com.example.passengerservice.dto.request.RatingRequest;
import com.example.passengerservice.dto.response.PassengerRatingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${feign.client.rating.name}", url = "${feign.client.rating.url}",
        path = "${feign.client.rating.path}", configuration = FeignClientConfiguration.class)
public interface RatingClient {

    @PostMapping("/passenger")
    PassengerRatingResponse createPassengerRecord(@RequestBody RatingRequest request);

    @DeleteMapping("/passenger/{id}")
    PassengerRatingResponse deletePassengerRecord(@PathVariable Long id);

}

