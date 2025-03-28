package com.example.carkotlin.services

import com.example.carkotlin.models.User
import com.example.carkotlin.models.dto.RegistrationRequest
import com.example.carkotlin.models.enum.Role
import com.example.carkotlin.repository.UserRepository
import com.example.carkotlin.utils.JwtUtility
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtility,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(username: String?, password: String?): Map<String, String> {
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User с таким именем не найден: $username")

        if (!passwordEncoder.matches(password, user.password)) {
            throw IllegalArgumentException("Неправильные данные для: $username")
        }

        val accessToken = jwtUtil.generateToken(user.username ?: "", true)

        userRepository.save(user)

        return mapOf(
            "accessToken" to accessToken,
        )
    }

    fun registerUser(registrationRequest: RegistrationRequest) {
        val username = registrationRequest.username
        if (userRepository.findByUsername(username) != null) {
            throw IllegalArgumentException("User с именем $username уже существует.")
        }

        val newUser = User().apply {
            this.username = username
            this.password = passwordEncoder.encode(registrationRequest.password)
            this.role = Role.USER
        }

        userRepository.save(newUser)
    }
}
