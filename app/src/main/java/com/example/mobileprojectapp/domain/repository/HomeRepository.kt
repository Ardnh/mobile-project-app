package com.example.mobileprojectapp.domain.repository

interface HomeRepository {
    suspend fun getMe()
    suspend fun getProjectSummary()
    suspend fun getProjectCategory()
    suspend fun getProjects()
}