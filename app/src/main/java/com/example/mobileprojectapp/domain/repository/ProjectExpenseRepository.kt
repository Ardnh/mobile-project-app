package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseRequestDto
import com.example.mobileprojectapp.domain.model.ProjectExpense

interface ProjectExpensesRepository {
    suspend fun createProjectExpenses(projectId: String, name: String) : Result<ProjectExpense>
    suspend fun updateProjectExpenses(id: String, projectId: String, name: String) : Result<ProjectExpense>
    suspend fun deleteProjectExpenses(id: String) : Result<Unit>
}