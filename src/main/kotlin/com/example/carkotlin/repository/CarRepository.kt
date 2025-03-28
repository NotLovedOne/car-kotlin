package com.example.carkotlin.repository

import com.example.carkotlin.models.Car
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CarRepository : JpaRepository<Car, UUID> {
}
