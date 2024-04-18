package org.example.pricecalculatorservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PriceCalculatorServiceApplication

fun main(args: Array<String>) {
    runApplication<PriceCalculatorServiceApplication>(*args)
}
