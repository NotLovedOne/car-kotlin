package com.example.carkotlin.models

import jakarta.persistence.*
import jakarta.validation.constraints.*
import java.util.*

@Entity
@Table(name = "cars")
class Car {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID = UUID.randomUUID()

    @field:NotBlank(message = "Make cannot be empty")
    @Column(name = "make", nullable = false)
    var make: String?= null

    @field:NotBlank(message = "Model cannot be empty")
    @Column(name = "model", nullable = false)
    var model: String?= null

    @field:Min(value = 1886, message = "Year cannot be before 1886")
    @field:Max(value = 2025, message = "Year cannot exceed the current year")
    @Column(name = "year", nullable = false)
    var year: Int?= null

    @field:Positive(message = "Price must be positive")
    @Column(name = "price", nullable = false)
    var price: Double?= null

    @field:Size(min = 17, max = 17, message = "VIN must be exactly 17 characters")
    @Column(name = "vin", unique = true, nullable = false, length = 17)
    var vin: String?= null
}

