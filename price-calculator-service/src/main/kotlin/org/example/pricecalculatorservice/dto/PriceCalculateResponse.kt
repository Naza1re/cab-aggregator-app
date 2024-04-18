package org.example.pricecalculatorservice.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class PriceCalculateResponse(
    val price: BigDecimal,
    val timestamp: LocalDateTime) {

}
