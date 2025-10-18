package com.example.mobileprojectapp.domain.repository

interface ProjectDetailsRepository {
    suspend fun getProjectById()
}