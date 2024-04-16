package org.example.pricecalculatorservice.service

import org.example.pricecalculatorservice.dto.PriceCalculateRequest
import org.example.pricecalculatorservice.dto.PriceCalculateResponse


interface PriceService {
    fun calculatePrice(priceRequest: PriceCalculateRequest): PriceCalculateResponse?


}
