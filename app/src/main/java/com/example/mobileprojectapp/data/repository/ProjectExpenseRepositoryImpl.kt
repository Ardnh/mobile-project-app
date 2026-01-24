package com.example.mobileprojectapp.data.repository

import com.example.mobileprojectapp.data.mapper.toDomain
import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseRequestDto
import com.example.mobileprojectapp.domain.model.ProjectExpense
import com.example.mobileprojectapp.domain.repository.ProjectExpensesRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProjectExpenseRepositoryImpl @Inject constructor(private val api : ApiService) : ProjectExpensesRepository {
    override suspend fun createProjectExpenses(projectId: String, name: String): Result<ProjectExpense> {
        return try {

            val req = CreateProjectExpenseRequestDto(
                projectId = projectId,
                name = name
            )
            val response = api.createProjectExpense(req)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success && body.data != null) {
                    val data = body.data.toDomain()
                    Result.success(data)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to create project expense"))
                }
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Network error: ${e.message()}", e))
        } catch (e: IOException) {
            Result.failure(Exception("No internet connection", e))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProjectExpenses(
        id: String,
        projectId: String,
        name: String
    ): Result<ProjectExpense> {
        return try {

            val req = UpdateProjectExpenseRequestDto(
                id = id,
                projectId = projectId,
                name = name
            )
            val response = api.updateProjectExpense(id, req)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success && body.data != null) {
                    val data = body.data.toDomain()
                    Result.success(data)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to update project expense"))
                }
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Network error: ${e.message()}", e))
        } catch (e: IOException) {
            Result.failure(Exception("No internet connection", e))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProjectExpenses(id: String): Result<Unit> {
        return try {
            val response = api.deleteProjectExpense(id)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to delete project expense"))
                }
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Network error: ${e.message()}", e))
        } catch (e: IOException) {
            Result.failure(Exception("No internet connection", e))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}