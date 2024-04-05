package org.example.carparkservice.model

import jakarta.persistence.*


@Entity
@Table(name = "car")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var model: String,
    var mark: String,
    var year: Int,
    var type: String
    ) {

}