package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistRequestDto
import com.example.mobileprojectapp.domain.model.CreateProjectTodolistRequest

interface ProjectTodolistRepository {
    suspend fun createProjectTodolist(req: CreateProjectTodolistRequest) : Result<Unit>
    suspend fun updateProjectTodolist(id: String, req: UpdateProjectTodolistRequestDto) : Result<Unit>
    suspend fun deleteProjectTodolist(id: String) : Result<Unit>
}