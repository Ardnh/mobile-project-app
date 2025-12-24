package com.example.mobileprojectapp.domain.model

import java.time.LocalDate

data class ProjectItem(
    val projectId: String,
    val userId: String,
    val name: String,
    val budget: String,
    val isCompleted: Boolean,
    val categoryName: String,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val totalTodolist: Long,
    val totalTodolistItemDone: Long,
    val totalTodolistItem: Long,
    val daysRemaining: Long,
    val daysRemainingStatus: String,
    val completionPercentage: Long
)

data class ProjectCategory (
    val categoryName: String,
    val total: Int
)

data class ProjectSummary (
    val totalBudgetUsed: String,
    val totalProjects: String,
    val totalProjectsDone: String
)

data class ProjectById(
    val id: String,
    val userId: String,
    val name: String,
    val budget: String,
    val startDate: String,
    val endDate: String,
    val categoryName: String,
    val budgetUsed: String,
    val totalTodolistItem: Long,
    val totalTodolistCompletedItem: Long,
    val projectExpenses: List<ProjectExpense>,
    val projectTodolists: List<ProjectTodolist>,
)

data class CreateProjectRequest(
    val userId: String,
    val name: String,
    val categoryName: String,
    val budget: Long,
    val startDate: String?,
    val endDate: String?,
    val isCompleted: Boolean?
)

data class UpdateProjectRequest(
    val userId: String,
    val name: String,
    val categoryName: String,
    val budget: Long,
    val startDate: String,
    val endDate: String,
    val isCompleted: Boolean
)