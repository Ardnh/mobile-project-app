package com.example.mobileprojectapp.data.mapper

import com.example.mobileprojectapp.data.remote.dto.UserByTokenResponseDto
import com.example.mobileprojectapp.domain.model.UserProfile

fun UserByTokenResponseDto.toDomain(): UserProfile {

    return UserProfile(
        id = this.id ?: "",
        email = this.email ?: "Unknown email",
        username = this.username ?: "Unknown name"
    )
}