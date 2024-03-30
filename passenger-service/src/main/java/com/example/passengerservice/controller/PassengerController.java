package com.example.passengerservice.controller;

import com.example.passengerservice.dto.response.PassengerPageResponse;
import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.response.PassengerListResponse;
import com.example.passengerservice.dto.response.PassengerResponse;
import com.example.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassengerById(
            @PathVariable Long id){
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    @GetMapping
    public ResponseEntity<PassengerListResponse> getAllPassengers(){
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(
           @Valid @RequestBody PassengerRequest passengerRequest)  {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(passengerService.createPassenger(passengerRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PassengerResponse> deletePassenger(
            @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(passengerService.deletePassenger(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(
            @Valid @RequestBody PassengerRequest passengerRequest,
            @PathVariable Long id){
        return ResponseEntity.ok(passengerService.updatePassenger(id,passengerRequest));
    }

    @GetMapping("/page")
    public ResponseEntity<PassengerPageResponse> getSortedListOfPassengers(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @RequestParam(name = "type",required = false) String type
    ){
        return ResponseEntity.ok(passengerService.getPassengerPage(offset,limit,type));
    }


}
