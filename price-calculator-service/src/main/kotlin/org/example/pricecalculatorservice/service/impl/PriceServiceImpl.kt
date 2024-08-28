package org.example.pricecalculatorservice.service.impl

import org.example.pricecalculatorservice.dto.PriceCalculateRequest
import org.example.pricecalculatorservice.dto.PriceCalculateResponse
import org.example.pricecalculatorservice.security.SecurityConstants.EMAIL
import org.example.pricecalculatorservice.security.SecurityConstants.FAMILY_NAME
import org.example.pricecalculatorservice.security.SecurityConstants.GIVEN_NAME
import org.example.pricecalculatorservice.security.SecurityConstants.ID
import org.example.pricecalculatorservice.security.SecurityConstants.PHONE
import org.example.pricecalculatorservice.security.SecurityConstants.USERNAME
import org.example.pricecalculatorservice.security.User
import org.example.pricecalculatorservice.service.PriceService
import org.example.pricecalculatorservice.service.TemperatureService
import org.example.pricecalculatorservice.utill.Constants
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Service
class PriceServiceImpl(private val temperatureService: TemperatureService) : PriceService {

    override fun calculatePrice(priceRequest: PriceCalculateRequest): PriceCalculateResponse? {

        val temperature = temperatureService.getTemperatureByWeather(priceRequest.city)
        val price = reducePriceByWeather(Constants.DEFAULT_PRICE, temperature)

        return PriceCalculateResponse(BigDecimal(price), LocalDateTime.now())
    }

    override fun extractUserInfo(jwt: Jwt): Any? {
        return User(
            phone = jwt.getClaimAsString(PHONE),
            surname = jwt.getClaimAsString(FAMILY_NAME),
            name1 = jwt.getClaimAsString(GIVEN_NAME),
            id = UUID.fromString(jwt.getClaimAsString(ID)),
            email = jwt.getClaimAsString(EMAIL),
            username1 = jwt.getClaimAsString(USERNAME)
        )
    }


    private fun reducePriceByWeather(price: Double, temperature: Double): Double {
        return if (temperature >= Constants.TEMPERATURE_BORDER) {
            price.times(Constants.PERCENT_OF_HOT_WEATHER)
        } else {
            price.times(Constants.PERCENT_OF_COLD_WEATHER)
        }
    }

}