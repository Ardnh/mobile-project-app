package com.example.mobileprojectapp.data.mapper

import com.example.mobileprojectapp.data.remote.dto.ProjectTodolistDto
import com.example.mobileprojectapp.domain.model.ProjectTodolist

fun ProjectTodolistDto.toDomain() : ProjectTodolist {

    val todolistItem = todolistItems.map { it.toDomain() }

    return ProjectTodolist(
        id = id,
        projectId = projectId,
        name = name,
        todolistItems = todolistItem,
        totalTodo = totalTodo,
        totalCompletedTodo = totalCompletedTodo,
        isTodolistCompleted = isTodolistCompleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
}