package com.example.mobileprojectapp.data.remote.dto

import com.google.gson.annotations.SerializedName

// User Request
data class CreateUserRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("full_name")
    val fullName: String
)

data class UpdateUserRequest(
    @SerializedName("username")
    val username: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("password")
    val password: String?
)

// User Response
data class UserByTokenResponse(
    @SerializedName("id")
    val id: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("username")
    val username: String?
)