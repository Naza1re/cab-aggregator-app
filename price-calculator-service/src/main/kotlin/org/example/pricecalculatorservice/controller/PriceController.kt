package org.example.pricecalculatorservice.controller

import org.example.pricecalculatorservice.service.PriceService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/price")
class PriceController(private val priceService: PriceService) {
}