package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.CreateProjectTodolistItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistItemRequestDto
import com.example.mobileprojectapp.domain.model.TodolistItem

interface ProjectTodolistItemRepository {
    suspend fun createProjectTodolistItem(todolistId: String, name: String, categoryName: String) : Result<TodolistItem>
    suspend fun updateProjectTodolistItem(id: String, projectTodolistId: String, name: String, categoryName: String, isCompleted: Boolean) : Result<TodolistItem>
    suspend fun deleteProjectTodolistItem(id: String) : Result<Unit>
}