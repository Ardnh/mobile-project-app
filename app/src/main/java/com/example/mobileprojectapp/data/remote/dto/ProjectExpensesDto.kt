package com.example.mobileprojectapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CreateProjectExpenseRequest(
    @SerializedName("project_id")
    val projectId: String,

    @SerializedName("name")
    val name: String
)

data class UpdateProjectExpenseRequest(
    @SerializedName("id")
    val id: String,

    @SerializedName("project_id")
    val projectId: String,

    @SerializedName("name")
    val name: String
)