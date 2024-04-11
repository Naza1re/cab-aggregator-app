package org.example.carparkservice.dto

data class CarRequest(
    var model: String,
    var mark: String,
    var year: Int,
    var type: String,
    var color: String,
    var number: String
) {
}