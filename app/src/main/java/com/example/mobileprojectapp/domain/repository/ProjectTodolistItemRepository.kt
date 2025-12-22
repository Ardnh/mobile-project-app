package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.CreateProjectTodolistItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistItemRequestDto

interface ProjectTodolistItemRepository {
    suspend fun createProjectTodolistItem(todolistId: String, name: String, categoryName: String) : Result<Unit>
    suspend fun updateProjectTodolistItem(id: String, req: UpdateProjectTodolistItemRequestDto) : Result<Unit>
    suspend fun deleteProjectTodolistItem(id: String) : Result<Unit>
}