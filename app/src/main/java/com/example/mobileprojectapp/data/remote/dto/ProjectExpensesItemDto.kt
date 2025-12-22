package com.example.mobileprojectapp.data.remote.dto
import com.google.gson.annotations.SerializedName


data class ExpensesItemDto(
    val id: String,
    @SerializedName("project_expenses_id")
    val projectExpensesId: String,
    val name: String,
    val amount: Long,
    @SerializedName("category_name")
    val categoryName: String,
)


data class CreateProjectExpenseItemRequestDto(
    @SerializedName("project_expense_id")
    val projectExpenseId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("amount")
    val amount: Long,

    @SerializedName("category_name")
    val categoryName: String
)

data class UpdateProjectExpenseItemRequestDto(
    @SerializedName("project_expense_id")
    val projectExpenseId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("amount")
    val amount: Double,

    @SerializedName("category_name")
    val categoryName: String
)