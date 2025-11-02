package com.example.mobileprojectapp.data.remote.dto
import com.google.gson.annotations.SerializedName

data class CreateProjectExpenseItemRequest(
    @SerializedName("project_expense_id")
    val projectExpenseId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("amount")
    val amount: Double,

    @SerializedName("category_name")
    val categoryName: String
)

data class UpdateProjectExpenseItemRequest(
    @SerializedName("project_expense_id")
    val projectExpenseId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("amount")
    val amount: Double,

    @SerializedName("category_name")
    val categoryName: String
)