package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.CreateProjectRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectRequestDto
import com.example.mobileprojectapp.domain.model.CreateProjectRequest
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.domain.model.ProjectCategory
import com.example.mobileprojectapp.domain.model.ProjectSummary
import com.example.mobileprojectapp.domain.model.ProjectById
import com.example.mobileprojectapp.domain.model.UpdateProjectRequest
import com.example.mobileprojectapp.presentation.features.home.ProjectByUserIdParams

interface ProjectsRepository {
    suspend fun getProjectCategory(userId: String) : Result<List<ProjectCategory>>
    suspend fun getProjectsByUserId(userId: String, param: ProjectByUserIdParams) : Result<List<ProjectItem>>
    suspend fun getProjectSummaryByUserId(userId: String) : Result<ProjectSummary>
    suspend fun getProjectById(id: String) : Result<ProjectById>
    suspend fun createProject(req: CreateProjectRequest) : Result<Unit>
    suspend fun updateProjectById(id: String, req: UpdateProjectRequest) : Result<Unit>
    suspend fun deleteProjectById(id: String) : Result<Unit>
}