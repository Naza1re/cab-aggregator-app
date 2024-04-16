package org.example.pricecalculatorservice.service.impl

import org.example.pricecalculatorservice.dto.PriceCalculateRequest
import org.example.pricecalculatorservice.dto.PriceCalculateResponse
import org.example.pricecalculatorservice.service.PriceService
import org.example.pricecalculatorservice.service.TemperatureService
import org.springframework.stereotype.Service

@Service
class PriceServiceImpl(private val temperatureService: TemperatureService) : PriceService {
    override fun calculatePrice(priceRequest: PriceCalculateRequest): PriceCalculateResponse? {


    }
}