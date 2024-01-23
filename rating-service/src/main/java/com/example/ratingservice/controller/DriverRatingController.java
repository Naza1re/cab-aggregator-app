package com.example.ratingservice.controller;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.DriverRatingListResponse;
import com.example.ratingservice.dto.responce.DriverRatingResponse;
import com.example.ratingservice.service.DriverRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating/driver")
@RequiredArgsConstructor
public class DriverRatingController {

    private final DriverRatingService driverRatingService;
    @GetMapping("/list")
    public ResponseEntity<DriverRatingListResponse> getListOfDrivers() {
        return ResponseEntity.ok(driverRatingService.getAllDriversRecords());
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverRatingResponse> getRateOfDriverById(@PathVariable Long driverId) {
        return ResponseEntity.ok(driverRatingService.getDriverById(driverId));
    }

    @PostMapping
    public ResponseEntity<DriverRatingResponse> createDriverRecord(@Valid @RequestBody CreateRequest createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driverRatingService.createDriver(createRequest));
    }

    @PutMapping
    public ResponseEntity<DriverRatingResponse> updateDriverRating(
            @Valid @RequestBody UpdateRequest driverRequest) {
        return ResponseEntity.ok(driverRatingService.updateDriverRate(driverRequest));
    }

    @DeleteMapping("/{driverID}")
    public ResponseEntity<DriverRatingResponse> deleteDriver(@PathVariable Long driverID) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(driverRatingService.deleteDriverRecord(driverID));
    }

}
