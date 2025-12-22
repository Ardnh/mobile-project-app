package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseItemRequestDto

interface ProjectExpensesItemRepository {
    suspend fun createProjectExpensesItem(projectExpensesId: String, name: String, amount: Long, categoryName: String) : Result<Unit>
    suspend fun updateProjectExpensesItem(id: String, req: UpdateProjectExpenseItemRequestDto) : Result<Unit>
    suspend fun deleteProjectExpensesItem(id: String) : Result<Unit>
}