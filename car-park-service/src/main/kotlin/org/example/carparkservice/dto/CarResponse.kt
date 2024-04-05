package org.example.carparkservice.dto

data class CarResponse(
    var id: Long,
    var model: String,
    var mark: String,
    var year: Int,
    var type: String,
    var color: String,
    var number: String
) {
}