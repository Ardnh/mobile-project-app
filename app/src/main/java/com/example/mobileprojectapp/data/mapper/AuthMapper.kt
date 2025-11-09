package com.example.mobileprojectapp.data.mapper

import com.example.mobileprojectapp.data.remote.dto.TokenDataDto
import com.example.mobileprojectapp.domain.model.TokenData

fun TokenDataDto.toDomain(): TokenData {
    return TokenData(
        token = this.token
    )
}