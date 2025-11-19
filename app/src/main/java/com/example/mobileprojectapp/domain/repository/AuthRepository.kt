package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.LoginRequestDto
import com.example.mobileprojectapp.data.remote.dto.RegisterRequestDto
import com.example.mobileprojectapp.domain.model.TokenData

interface AuthRepository {
    suspend fun login(req: LoginRequestDto) : Result<TokenData>
    suspend fun register(req: RegisterRequestDto) : Result<Unit>
}