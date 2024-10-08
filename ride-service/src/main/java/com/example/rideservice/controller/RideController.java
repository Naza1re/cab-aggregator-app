package com.example.rideservice.controller;

import com.example.rideservice.dto.request.RideRequest;
import com.example.rideservice.dto.response.RideListResponse;
import com.example.rideservice.dto.response.RidePageResponse;
import com.example.rideservice.dto.response.RideResponse;
import com.example.rideservice.service.RideService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/rides")
public class RideController {

    private final RideService rideService;

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER')")
    @PostMapping
    public ResponseEntity<RideResponse> findRide(
            @Valid @RequestBody RideRequest rideRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rideService.findRide(rideRequest));
    }

    @PreAuthorize("hasAnyRole('ROLE_DRIVER')")
    @PutMapping("/{rideId}/start")
    public ResponseEntity<RideResponse> startRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(rideService.startRide(rideId));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RideResponse> getRideById(@PathVariable Long id) {
        return ResponseEntity.ok(rideService.getRideById(id));
    }

    @GetMapping("/page")
    public ResponseEntity<RidePageResponse> getPaginationPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @RequestParam(name = "type", required = false) String type
    ) {
        return ResponseEntity.ok(rideService.getRidePage(offset, limit, type));
    }

    @PreAuthorize("hasAnyRole('ROLE_DRIVER')")
    @PutMapping("/{rideId}/end")
    public ResponseEntity<RideResponse> endRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(rideService.endRide(rideId));
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER')")
    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<RideListResponse> getListOfRidesByPassengerId(@PathVariable Long passengerId) {
        return ResponseEntity.ok(rideService.getListOfRidesByPassengerId(passengerId));
    }

    @PreAuthorize("hasAnyRole('ROLE_DRIVER')")
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<RideListResponse> getListOfRidesByDriverId(@PathVariable Long driverId) {
        return ResponseEntity.ok(rideService.getListOfRidesByDriverId(driverId));
    }
}
