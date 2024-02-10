package com.example.end2end.client;

import com.example.end2end.config.FeignClientConfiguration;
import com.example.end2end.dto.request.DriverRequest;
import com.example.end2end.dto.response.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${feign.client.driver.name}", url = "${feign.client.driver.url}",
        path = "${feign.client.driver.path}", configuration = FeignClientConfiguration.class)
public interface DriverFeignClient {
    @PostMapping
    DriverResponse createDriver(@RequestBody DriverRequest driverRequest);


}
