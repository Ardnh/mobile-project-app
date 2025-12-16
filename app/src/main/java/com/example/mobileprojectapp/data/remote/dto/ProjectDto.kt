package com.example.mobileprojectapp.data.remote.dto

import com.google.gson.annotations.SerializedName

//  ============================= PARAMS =============================
data class ProjectByUserIdParamsDto (
    val limit: Int,
    val page: Int,
    @SerializedName("sort_by")
    val sortBy: String,
    @SerializedName("sort_order")
    val sortOrder: String,
    val search: String
)

data class CreateProjectRequestDto(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("budget")
    val budget: Long,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("is_completed")
    val isCompleted: Boolean?
)

data class UpdateProjectRequestDto(
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("budget")
    val budget: Long?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("is_completed")
    val isCompleted: Boolean?
)

data class ProjectByUserIdDto(
    @SerializedName("project_id")
    val projectId: String,
    @SerializedName("user_id")
    val userId: String,
    val name: String,
    val budget: Long,
    @SerializedName("is_completed")
    val isCompleted: Boolean,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("total_todolist")
    val totalTodolist: Long,
    @SerializedName("total_todolist_item_done")
    val totalTodolistItemDone: Long,
    @SerializedName("total_todolist_item")
    val totalTodolistItem: Long,
    @SerializedName("days_remaining")
    val daysRemaining: Long,
    @SerializedName("days_remaining_status")
    val daysRemainingStatus: String,
    @SerializedName("completion_percentage")
    val completionPercentage: Long
)

data class ProjectCategoryByUserIdDto(
    @SerializedName("category_name")
    val categoryName: String,
    val total: Int,
)

data class ProjectSummaryByUserIdDto(
    @SerializedName("total_budget_used")
    val totalBudgetUsed: Long,
    @SerializedName("total_projects")
    val totalProjects: Long,
    @SerializedName("total_projects_done")
    val totalProjectsDone: Long,
)

data class ProjectByIdDto(
    val id: String,
    @SerializedName("user_id")
    val userId: String,
    val name: String,
    val budget: Long,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("budget_used")
    val budgetUsed: Long,
    @SerializedName("total_todolist_item")
    val totalTodolistItem: Long,
    @SerializedName("total_todolist_completed_item")
    val totalTodolistCompletedItem: Long,
    @SerializedName("project_expenses")
    val projectExpenses: List<ProjectExpenseDto>,
    @SerializedName("project_todolists")
    val projectTodolists: List<ProjectTodolistDto>,
)

