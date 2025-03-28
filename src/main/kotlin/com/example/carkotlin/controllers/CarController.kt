package com.example.carkotlin.controllers

import com.example.carkotlin.models.Car
import com.example.carkotlin.services.CarService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/cars")
class CarController(
    private val carService: CarService
) {
    private val logger = LoggerFactory.getLogger(CarController::class.java)

    @GetMapping
    fun getAllCars(): List<Car> {
        logger.info("GET /cars")
        return carService.getAllCars()
    }

    @GetMapping("/{id}")
    fun getCarById(@PathVariable id: UUID): ResponseEntity<Car> {
        logger.info("GET /cars/$id")
        val car = carService.getCarById(id)
        return if (car != null) {
            ResponseEntity.ok(car)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createCar(@RequestBody @Validated car: Car): ResponseEntity<Car> {
        logger.info("POST /cars -> $car")
        val newCar = carService.createCar(car)
        return ResponseEntity.ok(newCar)
    }

    @PutMapping("/{id}")
    fun updateCar(
        @PathVariable id: UUID,
        @RequestBody @Validated updated: Car
    ): ResponseEntity<Car> {
        logger.info("PUT /cars/$id -> $updated")
        val car = carService.updateCar(id, updated)
        return if (car != null) {
            ResponseEntity.ok(car)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCar(@PathVariable id: UUID): ResponseEntity<Unit> {
        logger.info("DELETE /cars/$id")
        return if (carService.deleteCar(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
