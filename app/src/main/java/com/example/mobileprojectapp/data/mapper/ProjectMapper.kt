package com.example.mobileprojectapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mobileprojectapp.data.remote.dto.ProjectByIdDto
import com.example.mobileprojectapp.data.remote.dto.ProjectByUserIdDto
import com.example.mobileprojectapp.data.remote.dto.ProjectCategoryByUserIdDto
import com.example.mobileprojectapp.data.remote.dto.ProjectSummaryByUserIdDto
import com.example.mobileprojectapp.domain.model.ProjectById
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.domain.model.ProjectCategory
import com.example.mobileprojectapp.domain.model.ProjectSummary
import com.example.mobileprojectapp.utils.toLocalDateOrNull
import com.example.mobileprojectapp.utils.toNumberFormat
import kotlin.math.roundToLong

@RequiresApi(Build.VERSION_CODES.O)
fun ProjectByUserIdDto.toDomain(): ProjectItem {
    return ProjectItem(
        projectId = projectId,
        userId = userId,
        name = name,
        budget = budget.toNumberFormat(),
        isCompleted = isCompleted,
        categoryName = categoryName,
        startDate = startDate.toLocalDateOrNull(),
        endDate = endDate.toLocalDateOrNull(),
        totalTodolist = totalTodolist,
        totalTodolistItemDone = totalTodolistItemDone,
        totalTodolistItem = totalTodolistItem,
        daysRemaining = daysRemaining,
        daysRemainingStatus = daysRemainingStatus,
        completionPercentage = completionPercentage
    )
}

fun ProjectCategoryByUserIdDto.toDomain() : ProjectCategory {
    return ProjectCategory(
        categoryName = this.categoryName,
        total = this.total
    )
}

fun ProjectSummaryByUserIdDto.toDomain(): ProjectSummary {
    return ProjectSummary(
        totalBudgetUsed = totalBudgetUsed.toNumberFormat(),
        totalProjects = totalProjects.toNumberFormat(),
        totalProjectsDone = totalProjectsDone.toNumberFormat()
    )
}

fun ProjectByIdDto.toDomain() : ProjectById {

    val projectExpenses = projectExpenses.map { it.toDomain() }
    val projectTodolist = projectTodolists.map { it.toDomain() }

    return ProjectById(
        id = id,
        userId = userId,
        name = name,
        budget = budget.toNumberFormat(),
        startDate = startDate.split("T").first(),
        endDate = endDate.split("T").first(),
        categoryName = categoryName,
        budgetUsed = budgetUsed.toNumberFormat(),
        totalTodolistItem = totalTodolistItem,
        totalTodolistCompletedItem = totalTodolistCompletedItem,
        daysRemaining = daysRemaining,
        daysRemainingStatus = daysRemainingStatus,
        projectExpenses = projectExpenses,
        projectTodolists = projectTodolist
    )
}