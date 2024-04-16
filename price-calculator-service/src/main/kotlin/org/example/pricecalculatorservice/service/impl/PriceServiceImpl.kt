package org.example.pricecalculatorservice.service.impl

import org.example.pricecalculatorservice.dto.PriceCalculateRequest
import org.example.pricecalculatorservice.dto.PriceCalculateResponse
import org.example.pricecalculatorservice.service.PriceService
import org.example.pricecalculatorservice.service.TemperatureService
import org.example.pricecalculatorservice.utill.Constants
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class PriceServiceImpl(private val temperatureService: TemperatureService) : PriceService {

    override fun calculatePrice(priceRequest: PriceCalculateRequest): PriceCalculateResponse? {

        val temperature = temperatureService.getTemperatureByWeather(priceRequest.city)
        val price = reducePriceByWeather(Constants.DEFAULT_PRICE, temperature)

        return PriceCalculateResponse(BigDecimal(price), LocalDateTime.now())
    }


    private fun reducePriceByWeather(price: Double, temperature: Double): Double {
        return if (temperature >= Constants.TEMPERATURE_BORDER) {
            price.times(Constants.PERCENT_OF_HOT_WEATHER)
        } else {
            price.times(Constants.PERCENT_OF_COLD_WEATHER)
        }
    }

}