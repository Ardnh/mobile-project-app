package com.example.mobileprojectapp.data.repository

import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseItemRequestDto
import com.example.mobileprojectapp.domain.repository.ProjectExpensesItemRepository
import javax.inject.Inject

class ProjectExpenseItemRepositoryImpl @Inject constructor(private val api : ApiService) : ProjectExpensesItemRepository {
    override suspend fun createProjectExpensesItem(req: CreateProjectExpenseItemRequestDto): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProjectExpensesItem(
        id: String,
        req: UpdateProjectExpenseItemRequestDto
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProjectExpensesItem(id: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}