package com.example.ratingservice.controller;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.PassengerListResponse;
import com.example.ratingservice.dto.responce.PassengerRatingResponse;
import com.example.ratingservice.service.PassengerRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating/passenger")
@RequiredArgsConstructor
public class PassengerRatingController {

    private final PassengerRatingService passengerRatingService;

    @GetMapping("/list")
    public ResponseEntity<PassengerListResponse> getListOfPassengers() {
        return ResponseEntity.ok(passengerRatingService.getAllPassengersRecords());
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<PassengerRatingResponse> getPassengerRateById(@PathVariable Long passengerId) {
        return ResponseEntity.ok(passengerRatingService.getPassengerRecordById(passengerId));
    }

    @PostMapping
    public ResponseEntity<PassengerRatingResponse> creatingPassenger(
            @Valid @RequestBody CreateRequest createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(passengerRatingService.createPassenger(createRequest));
    }

    @PutMapping
    public ResponseEntity<PassengerRatingResponse> updatePassengerRate(
            @Valid @RequestBody UpdateRequest updateRequest) {
        return ResponseEntity.ok(passengerRatingService.updatePassengerRating(updateRequest));
    }

    @DeleteMapping("/{passengerId}")
    public ResponseEntity<PassengerRatingResponse> deletePassengerRecord(
            @PathVariable Long passengerId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(passengerRatingService.deletePassengerRecord(passengerId));
    }
}
