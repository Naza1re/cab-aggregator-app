package org.example.pricecalculatorservice.service

import org.example.pricecalculatorservice.dto.PriceCalculateRequest
import org.example.pricecalculatorservice.dto.PriceCalculateResponse
import org.springframework.security.oauth2.jwt.Jwt


interface PriceService {
    fun calculatePrice(priceRequest: PriceCalculateRequest): PriceCalculateResponse?
    fun extractUserInfo(jwt: Jwt): Any?
}
