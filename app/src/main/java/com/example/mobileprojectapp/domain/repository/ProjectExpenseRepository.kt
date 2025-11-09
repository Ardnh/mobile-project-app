package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseRequestDto

interface ProjectExpensesRepository {
    suspend fun createProjectExpenses(req: CreateProjectExpenseRequestDto) : Result<Unit>
    suspend fun updateProjectExpenses(id: String, req: UpdateProjectExpenseRequestDto) : Result<Unit>
    suspend fun deleteProjectExpenses(id: String) : Result<Unit>
}