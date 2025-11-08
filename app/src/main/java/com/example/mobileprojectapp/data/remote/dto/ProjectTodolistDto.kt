package com.example.mobileprojectapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProjectTodolist(
    val id: String,
    @SerializedName("project_id")
    val projectId: String,
    val name: String,
    @SerializedName("todolist_items")
    val todolistItems: List<TodolistItem>,
    @SerializedName("total_todo")
    val totalTodo: Long,
    @SerializedName("total_completed_todo")
    val totalCompletedTodo: Long,
    @SerializedName("is_todolist_completed")
    val isTodolistCompleted: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("deleted_at")
    val deletedAt: String?,
)


data class CreateProjectTodolistRequest(
    @SerializedName("project_id")
    val projectId: String,

    @SerializedName("name")
    val name: String
)

data class UpdateProjectTodolistRequest(
    @SerializedName("id")
    val id: String,

    @SerializedName("project_id")
    val projectId: String,

    @SerializedName("name")
    val name: String,

)

