package com.example.mobileprojectapp.domain.repository

interface ProjectsRepository {
    suspend fun getProjectCategory()
    suspend fun getProjects()
}