package com.example.passengerservice.controller;

import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.response.PassengerListResponse;
import com.example.passengerservice.dto.response.PassengerPageResponse;
import com.example.passengerservice.dto.response.PassengerResponse;
import com.example.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassengerById(
            @PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    @GetMapping
    public ResponseEntity<PassengerListResponse> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(
            @AuthenticationPrincipal OAuth2User user) {
        PassengerRequest passengerRequest = passengerService.getPassengerRequestFromOauth2User(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(passengerService.createPassenger(passengerRequest));

    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<PassengerResponse> deletePassenger(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(passengerService.deletePassenger(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(
            @Valid @RequestBody PassengerRequest passengerRequest,
            @PathVariable Long id) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, passengerRequest));
    }

    @GetMapping("/page")
    public ResponseEntity<PassengerPageResponse> getSortedListOfPassengers(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @RequestParam(name = "type", required = false) String type
    ) {
        return ResponseEntity.ok(passengerService.getPassengerPage(offset, limit, type));
    }


}
