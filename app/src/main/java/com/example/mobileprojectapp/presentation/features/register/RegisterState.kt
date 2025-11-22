package com.example.mobileprojectapp.presentation.features.register

data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)