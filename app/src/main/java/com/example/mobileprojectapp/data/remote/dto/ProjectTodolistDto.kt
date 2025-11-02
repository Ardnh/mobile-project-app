package com.example.mobileprojectapp.data.remote.dto

import com.google.gson.annotations.SerializedName

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