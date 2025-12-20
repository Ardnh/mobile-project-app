package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseRequestDto

interface ProjectExpensesRepository {
    suspend fun createProjectExpenses(projectId: String, name: String) : Result<Unit>
    suspend fun updateProjectExpenses(id: String, projectId: String, name: String) : Result<Unit>
    suspend fun deleteProjectExpenses(id: String) : Result<Unit>
}