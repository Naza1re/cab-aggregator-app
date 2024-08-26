package org.example.hiringdriverservice.controller;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.example.hiringdriverservice.dto.DriverApplyRequest;
import org.example.hiringdriverservice.dto.DriverApplyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hiring-driver")
public class HiringDriverController {

    private final RuntimeService runtimeService;


    @GetMapping("/apply")
    public ResponseEntity<DriverApplyResponse> applyHiringDriver(@RequestBody DriverApplyRequest request) {
        return null;
    }

}
