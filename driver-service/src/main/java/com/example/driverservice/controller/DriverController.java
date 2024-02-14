package com.example.driverservice.controller;

import com.example.driverservice.dto.request.DriverRequest;
import com.example.driverservice.dto.response.DriverListResponse;
import com.example.driverservice.dto.response.DriverPageResponse;
import com.example.driverservice.dto.response.DriverResponse;
import com.example.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final DriverService driverService;

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getDriverById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @GetMapping
    public ResponseEntity<DriverListResponse> getListOfDrivers() {
        return ResponseEntity.ok(driverService.getListOfDrivers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponse> updateDriver(
            @PathVariable Long id,
            @Valid @RequestBody DriverRequest driverRequest) {
        return ResponseEntity.ok(driverService.updateDriver(id, driverRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DriverResponse> deleteDriver(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(driverService.deleteDriver(id));
    }

    @PostMapping
    public ResponseEntity<DriverResponse> createDriver(
            @Valid @RequestBody DriverRequest driverRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driverService.createDriver(driverRequest));
    }

    @GetMapping("/page")
    public ResponseEntity<DriverPageResponse> getSortedListOfDrivers(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @RequestParam(name = "type", required = false) String type
    ) {
        return ResponseEntity.ok(driverService.getDriverPage(offset, limit, type));
    }

    @GetMapping("/available")
    public ResponseEntity<DriverListResponse> getAvailableDrivers() {
        return ResponseEntity.ok(driverService.getAvailableDrivers());
    }

    @PutMapping("/{driverId}/status")
    public ResponseEntity<DriverResponse> changeStatus(@PathVariable Long driverId) {
        return ResponseEntity.ok(driverService.changeStatus(driverId));
    }

}
