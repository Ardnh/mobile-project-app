package com.example.mobileprojectapp.data.remote.dto
import com.google.gson.annotations.SerializedName

data class ProjectExpenseDto(
    val id: String,
    @SerializedName("project_id")
    val projectId: String,
    val name: String,
    @SerializedName("expenses_used")
    val expensesUsed: Long,
    @SerializedName("expenses_item")
    val expensesItem: List<ExpensesItemDto>,
)

data class CreateProjectExpenseRequestDto(
    @SerializedName("project_id")
    val projectId: String,

    @SerializedName("name")
    val name: String
)

data class UpdateProjectExpenseRequestDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("project_id")
    val projectId: String,

    @SerializedName("name")
    val name: String
)