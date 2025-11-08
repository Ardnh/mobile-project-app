package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.LoginRequest
import com.example.mobileprojectapp.data.remote.dto.RegisterRequest
import com.example.mobileprojectapp.data.remote.dto.TokenData

interface AuthRepository {
    suspend fun login(req: LoginRequest) : Result<TokenData>
    suspend fun register(req: RegisterRequest) : Result<Unit>
}