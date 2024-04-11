package org.example.carparkservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CarParkServiceApplication

fun main(args: Array<String>) {
    runApplication<CarParkServiceApplication>(*args)
}
