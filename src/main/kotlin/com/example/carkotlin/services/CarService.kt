package com.example.carkotlin.services

import com.example.carkotlin.models.Car
import com.example.carkotlin.repository.CarRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CarService(
    private val carRepository: CarRepository
) {
    fun getAllCars(): List<Car> = carRepository.findAll()

    fun getCarById(id: UUID): Car? = carRepository.findById(id).orElse(null)

    fun createCar(car: Car): Car = carRepository.save(car)

    fun updateCar(id: UUID, updated: Car): Car? {
        val existing = carRepository.findById(id).orElse(null) ?: return null
        existing.vin = updated.vin
        existing.year = updated.year
        existing.price = updated.price
        existing.model = updated.model
        existing.make = updated.make

        return carRepository.save(existing)
    }

    fun deleteCar(id: UUID): Boolean {
        if (!carRepository.existsById(id)) {
            return false
        }
        carRepository.deleteById(id)
        return true
    }
}
