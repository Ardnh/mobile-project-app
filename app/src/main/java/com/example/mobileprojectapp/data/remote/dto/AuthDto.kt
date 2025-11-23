package com.example.mobileprojectapp.data.remote.dto

import com.google.gson.annotations.SerializedName

// Auth Request
data class LoginRequestDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class RegisterRequestDto(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)

// Auth Response
data class LoginResponseDto(
    @SerializedName("token")
    val token: String
)

//data class TokenDataDto (
//    @SerializedName("token")
//    val token: String
//)