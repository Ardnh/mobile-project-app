package com.example.mobileprojectapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateProjectTodolistItemRequest(
    @SerializedName("project_todolist_id")
    val projectTodolistId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("category_name")
    val categoryName: String,

    @SerializedName("is_completed")
    val isCompleted: Boolean  // *bool di Go = Boolean di Kotlin (tidak null karena required)
)

data class UpdateProjectTodolistItemRequest(
    @SerializedName("id")
    val id: String,

    @SerializedName("project_todolist_id")
    val projectTodolistId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("category_name")
    val categoryName: String,

    @SerializedName("is_completed")
    val isCompleted: Boolean,

    @SerializedName("updated_at")
    val updatedAt: String  // ISO 8601 format: "2024-01-15T10:30:00Z"
)