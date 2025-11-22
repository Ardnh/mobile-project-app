package com.example.mobileprojectapp.presentation.features.home

import com.example.mobileprojectapp.domain.model.ProjectByUserId

data class HomeState(
    val projectSummary: HomeProjectSummaryState = HomeProjectSummaryState(),
    val projectCategory: List<String> = emptyList<String>(),
    val projectList: List<ProjectByUserId> = emptyList<ProjectByUserId>(),
    val projectSummaryError: String?,
    val projectCategoryError: String?,
    val projectListError: String?
)
data class HomeProjectSummaryState(
    val totalProject: Int = 0,
    val totalProjectDone: Int = 0,
    val totalBudgetUsed: Int = 0
)

data class ProjectByUserIdParams (
    val limit: Int = 10,
    val page: Int = 1,
    val sortBy: String = "created_at",
    val sortOrder: String = "desc",
    val search: String = ""
)