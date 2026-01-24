package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.CreateProjectTodolistRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistRequestDto
import com.example.mobileprojectapp.domain.model.ProjectTodolist

interface ProjectTodolistRepository {
    suspend fun createProjectTodolist(projectId: String, name: String) : Result<ProjectTodolist>
    suspend fun updateProjectTodolist(id: String, projectId: String, name: String) : Result<ProjectTodolist>
    suspend fun deleteProjectTodolist(id: String) : Result<Unit>
}