package com.example.carkotlin.controllers

import com.example.carkotlin.models.dto.LoginRequest
import com.example.carkotlin.models.dto.RegistrationRequest
import com.example.carkotlin.services.AuthService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Map<String, String>> {
        logger.info("login request: {}", loginRequest)
        val tokens = authService.login(loginRequest.username, loginRequest.password)
        return ResponseEntity.ok(tokens)
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody registrationRequest: RegistrationRequest): ResponseEntity<String> {
        logger.info("register request: {}", registrationRequest)
        authService.registerUser(registrationRequest)
        return ResponseEntity.ok("User registered successfully")
    }
}
