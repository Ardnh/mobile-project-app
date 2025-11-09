package com.example.mobileprojectapp.data.mapper

import com.example.mobileprojectapp.data.remote.dto.TodolistItemDto
import com.example.mobileprojectapp.domain.model.TodolistItem

fun TodolistItemDto.toDomain(): TodolistItem {
    return TodolistItem(
        id = id,
        projectTodolistId = projectTodolistId,
        name = name,
        categoryName = categoryName,
        isCompleted = isCompleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
}