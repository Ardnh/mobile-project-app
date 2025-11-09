package com.example.mobileprojectapp.data.remote.dto

import com.google.gson.annotations.SerializedName

// User Request
data class CreateUserRequestDto(
    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("full_name")
    val fullName: String
)

data class UpdateUserRequestDto(
    @SerializedName("username")
    val username: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("password")
    val password: String?
)

// User Response
data class UserByTokenResponseDto(
    @SerializedName("id")
    val id: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("username")
    val username: String?
)