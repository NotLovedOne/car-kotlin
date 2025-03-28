package com.example.carkotlin.utils


import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val jwtUtility: JwtUtility
) : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val token = authorizationHeader.substring(7)

            if (jwtUtility.validateToken(token)) {
                val username = jwtUtility.extractUsername(token)
                val role = jwtUtility.extractRole(token)

                val authorities: List<GrantedAuthority> =
                    listOf(SimpleGrantedAuthority("ROLE_$role"))

                val authentication = UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
                )

                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        try {
            filterChain.doFilter(request, response)
        } catch (ex: IOException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Ошибка аутентификации: ${ex.message}")
            throw RuntimeException(ex)
        }
    }
}
