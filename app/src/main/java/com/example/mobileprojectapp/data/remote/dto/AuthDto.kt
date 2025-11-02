package com.example.mobileprojectapp.data.remote.dto

import com.google.gson.annotations.SerializedName

// Auth Request
data class LoginRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)

data class RegisterRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,
)

// Auth Response
data class LoginResponse(
    @SerializedName("data")
    val data: TokenData,
)

data class TokenData (
    @SerializedName("token")
    val token: String
)


data class RegisterResponse (
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: Boolean,
)