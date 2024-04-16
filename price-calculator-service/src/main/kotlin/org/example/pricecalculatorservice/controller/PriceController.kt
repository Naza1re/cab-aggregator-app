package org.example.pricecalculatorservice.controller

import org.example.pricecalculatorservice.dto.PriceCalculateRequest
import org.example.pricecalculatorservice.dto.PriceCalculateResponse
import org.example.pricecalculatorservice.service.PriceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/price")
class PriceController(private val priceService: PriceService) {


    @GetMapping
    fun getCalculatePrice(@RequestBody priceRequest:PriceCalculateRequest): ResponseEntity<PriceCalculateResponse>{
        return ResponseEntity.ok(priceService.calculatePrice(priceRequest));

    }
}