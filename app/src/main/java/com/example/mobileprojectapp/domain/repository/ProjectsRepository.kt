package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.CreateProjectRequestDto
import com.example.mobileprojectapp.data.remote.dto.ProjectByUserIdParamsDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectRequestDto
import com.example.mobileprojectapp.domain.model.ProjectByUserId
import com.example.mobileprojectapp.domain.model.ProjectCategory
import com.example.mobileprojectapp.domain.model.ProjectSummary
import com.example.mobileprojectapp.domain.model.ProjectById

interface ProjectsRepository {
    suspend fun getProjectCategory(userId: String) : Result<List<ProjectCategory>>
    suspend fun getProjectsByUserId(userId: String, param: ProjectByUserIdParamsDto) : Result<List<ProjectByUserId>>
    suspend fun getProjectSummaryByUserId(userId: String) : Result<ProjectSummary>
    suspend fun getProjectById(id: String) : Result<ProjectById>
    suspend fun createProject(req: CreateProjectRequestDto) : Result<Unit>
    suspend fun updateProjectById(id: String, req: UpdateProjectRequestDto) : Result<Unit>
    suspend fun deleteProjectById(id: String) : Result<Unit>
}