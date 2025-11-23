package com.example.mobileprojectapp.data.mapper

import com.example.mobileprojectapp.data.remote.dto.LoginResponseDto
import com.example.mobileprojectapp.domain.model.TokenData

fun LoginResponseDto.toDomain(): TokenData {
    return TokenData(
        token = this.token
    )
}