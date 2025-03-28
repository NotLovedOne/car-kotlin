package com.example.carkotlin.utils


import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtUtility(
    private val userRepository: UserRepository
) {
    companion object {
        private const val SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
        private const val ACCESS_TOKEN_VALIDITY = 15 * 60 * 1000 // 15 минут
        // private const val REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000 // Пример: 7 дней
    }

    private val key: Key = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray())

    fun generateToken(username: String, isAccessToken: Boolean): String {
        val user: User? = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("Пользователь не найден: $username")

        // При желании можно сделать отдельную логику,
        // если нужен разный срок жизни для Access/Refresh-токена.
        val expirationTime = ACCESS_TOKEN_VALIDITY

        return Jwts.builder()
            .setSubject(username)
            .claim("role", user.role?.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            true
        } catch (ex: Exception) {
            println("Token validation failed: ${ex.message}")
            false
        }
    }

    fun extractRole(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .get("role", String::class.java)
    }

    fun extractUsername(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }
}
