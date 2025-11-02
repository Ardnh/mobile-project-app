package com.example.mobileprojectapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateProjectRequest(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("category_name")
    val categoryName: String,

    @SerializedName("budget")
    val budget: Double?,

    @SerializedName("start_date")
    val startDate: String?,

    @SerializedName("end_date")
    val endDate: String?,

    @SerializedName("is_completed")
    val isCompleted: Boolean?
)

data class UpdateProjectRequest(
    @SerializedName("name")
    val name: String?,

    @SerializedName("category")
    val category: String?,

    @SerializedName("budget")
    val budget: Double?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("start_date")
    val startDate: String?,

    @SerializedName("end_date")
    val endDate: String?,

    @SerializedName("is_completed")
    val isCompleted: Boolean?
)
