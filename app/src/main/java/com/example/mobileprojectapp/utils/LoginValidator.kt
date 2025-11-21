package com.example.mobileprojectapp.utils

object LoginValidator {

    data class Result(
        val usernameError: String? = null,
        val passwordError: String? = null,
        val isValid: Boolean
    )

    fun validate(username: String, password: String): Result {
        var usernameErr: String? = null
        var passwordErr: String? = null

        if (username.isBlank()) {
            usernameErr = "Username is required"
        }

        if (password.isBlank()) {
            passwordErr = "Password is required"
        } else if (password.length < 6) {
            passwordErr = "Password must be at least 6 characters"
        }

        return Result(
            usernameError = usernameErr,
            passwordError = passwordErr,
            isValid = usernameErr == null && passwordErr == null
        )
    }
}
