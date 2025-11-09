package com.example.mobileprojectapp.data.repository

import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseRequestDto
import com.example.mobileprojectapp.domain.repository.ProjectExpensesRepository
import javax.inject.Inject

class ProjectExpenseRepositoryImpl @Inject constructor(private val api : ApiService) : ProjectExpensesRepository {
    override suspend fun createProjectExpenses(req: CreateProjectExpenseRequestDto): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProjectExpenses(
        id: String,
        req: UpdateProjectExpenseRequestDto
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProjectExpenses(id: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}