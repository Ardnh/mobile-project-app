package com.example.mobileprojectapp.presentation.features.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null
)
