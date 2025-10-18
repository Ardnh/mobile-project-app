package com.example.mobileprojectapp.domain.repository

interface AuthRepository {
    suspend fun login()
    suspend fun register()
}