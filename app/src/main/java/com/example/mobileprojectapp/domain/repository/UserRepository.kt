package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.domain.model.UserProfile

interface UserRepository {
    suspend fun getUserProfile() : Result<UserProfile>
}