package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.domain.model.TokenData

interface AuthRepository {
    suspend fun login(username: String, password: String) : Result<TokenData>
    suspend fun register(username: String, email: String, password: String) : Result<Unit>
}