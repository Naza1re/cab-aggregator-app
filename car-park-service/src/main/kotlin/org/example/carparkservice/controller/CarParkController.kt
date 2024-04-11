package org.example.carparkservice.controller

import org.example.carparkservice.dto.CarListResponse
import org.example.carparkservice.dto.CarOwnerRequest
import org.example.carparkservice.dto.CarRequest
import org.example.carparkservice.dto.CarResponse
import org.example.carparkservice.service.CarParkService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RequestMapping("/api/v1/car-park")
@RestController
class CarParkController(private var carParkService: CarParkService) {

    @GetMapping("/{id}")
    fun getCarById(@PathVariable id: Long): ResponseEntity<CarResponse> {
        return ResponseEntity.ok(carParkService.getCarById(id))
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    fun save(@RequestBody car: CarRequest): ResponseEntity<CarResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(carParkService.createCar(car))
    }

    @PreAuthorize("hasAnyRole('ROLE_DRIVER')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<CarResponse> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(carParkService.deleteCarById(id))
    }

    @GetMapping
    fun list(): ResponseEntity<CarListResponse> {
        return ResponseEntity.ok(carParkService.findAllCars())
    }

    @PreAuthorize("hasAnyRole('ROLE_DRIVER')")
    @PutMapping("/set/owner")
    fun setOwner(@RequestBody carOwnerRequest: CarOwnerRequest): ResponseEntity<CarResponse> {
        return ResponseEntity.ok(carParkService.setDriverToCar(carOwnerRequest))
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody carRequest: CarRequest): ResponseEntity<CarResponse> {
        return ResponseEntity.ok(carParkService.updateCarById(id, carRequest))
    }


}