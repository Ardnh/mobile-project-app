package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.CreateProjectTodolistRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistRequestDto

interface ProjectTodolistRepository {
    suspend fun createProjectTodolist(projectId: String, name: String) : Result<Unit>
    suspend fun updateProjectTodolist(id: String, projectId: String, name: String) : Result<Unit>
    suspend fun deleteProjectTodolist(id: String) : Result<Unit>
}