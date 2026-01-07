package com.example.mobileprojectapp

import com.example.mobileprojectapp.data.remote.dto.LoginRequestDto
import com.example.mobileprojectapp.data.remote.dto.LoginResponseDto
import com.example.mobileprojectapp.data.remote.dto.RegisterRequestDto
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit Test sederhana untuk memvalidasi Data Transfer Object (DTO) autentikasi.
 */
class AuthUnitTest {

    @Test
    fun `login request dto should hold correct data`() {
        val email = "user@example.com"
        val password = "password123"

        val request = LoginRequestDto(email, password)

        assertEquals(email, request.email)
        assertEquals(password, request.password)
    }

    @Test
    fun `register request dto should hold correct data`() {
        val username = "ardanhilal"
        val email = "ardan@test.com"
        val password = "securepassword"

        val request = RegisterRequestDto(username, email, password)

        assertEquals(username, request.username)
        assertEquals(email, request.email)
        assertEquals(password, request.password)
    }

    @Test
    fun `login response dto should hold correct data`() {
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
        val expiredAt = "2026-01-06T14:00:00Z"

        val response = LoginResponseDto(token, expiredAt)

        assertEquals(token, response.token)
        assertEquals(expiredAt, response.expiredAt)
    }
}
