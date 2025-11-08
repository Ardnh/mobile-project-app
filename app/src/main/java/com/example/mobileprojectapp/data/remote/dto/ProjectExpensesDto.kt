package com.example.mobileprojectapp.data.remote.dto
import com.google.gson.annotations.SerializedName

data class ProjectExpense(
    val id: String,
    @SerializedName("project_id")
    val projectId: String,
    val name: String,
    @SerializedName("expenses_used")
    val expensesUsed: Long,
    @SerializedName("expenses_item")
    val expensesItem: List<ExpensesItem>,
)

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