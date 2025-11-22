package com.example.mobileprojectapp.utils
import android.util.Patterns

object RegisterValidator {

    data class Result(
        val emailError: String? = null,
        val usernameError: String? = null,
        val passwordError: String? = null,
        val isValid: Boolean
    )

    fun validate(email: String, username: String, password: String): Result {
        var emailErr: String? = null
        var usernameErr: String? = null
        var passwordErr: String? = null

        // Validasi username
        if (username.isBlank()) {
            usernameErr = "Username is required"
        }

        // Validasi email
        if (email.isBlank()) {
            emailErr = "Email is required"
        } else if (!isValidEmail(email)) {
            emailErr = "Invalid email format"
        }

        // Validasi password
        if (password.isBlank()) {
            passwordErr = "Password is required"
        } else if (password.length < 6) {
            passwordErr = "Password must be at least 6 characters"
        }

        return Result(
            emailError = emailErr,
            usernameError = usernameErr,
            passwordError = passwordErr,
            isValid = emailErr == null && usernameErr == null && passwordErr == null
        )
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}