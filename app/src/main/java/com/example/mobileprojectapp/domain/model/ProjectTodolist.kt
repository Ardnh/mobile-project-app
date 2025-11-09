package com.example.mobileprojectapp.domain.model


data class ProjectTodolist(
    val id: String,
    val projectId: String,
    val name: String,
    val todolistItems: List<TodolistItem>,
    val totalTodo: Long,
    val totalCompletedTodo: Long,
    val isTodolistCompleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?,
)


data class CreateProjectTodolistRequest(
    val projectId: String,
    val name: String
)

data class UpdateProjectTodolistRequest(
    val id: String,
    val projectId: String,
    val name: String
)