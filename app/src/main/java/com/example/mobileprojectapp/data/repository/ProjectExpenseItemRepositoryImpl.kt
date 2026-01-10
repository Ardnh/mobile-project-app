package com.example.mobileprojectapp.data.repository

import com.example.mobileprojectapp.data.mapper.toDomain
import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseItemRequestDto
import com.example.mobileprojectapp.domain.repository.ProjectExpensesItemRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProjectExpenseItemRepositoryImpl @Inject constructor(private val api : ApiService) : ProjectExpensesItemRepository {
    override suspend fun createProjectExpensesItem(projectExpensesId: String, name: String, amount: Long, categoryName: String): Result<Unit> {
        return try {

            val req = CreateProjectExpenseItemRequestDto(
                projectExpenseId = projectExpensesId,
                name = name,
                amount = amount,
                categoryName = categoryName
            )
            val response = api.createProjectExpenseItem(req)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success && body.data != null) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to get project category"))
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

    override suspend fun updateProjectExpensesItem(id: String, projectExpeseId: String, name: String, amount: Long, categoryName: String): Result<Unit> {
        return try {

            val req = UpdateProjectExpenseItemRequestDto(
                projectExpenseId = projectExpeseId,
                name = name,
                amount = amount,
                categoryName = categoryName
            )

            val response = api.updateProjectExpenseItem(id, req)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to update project expense item"))
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

    override suspend fun deleteProjectExpensesItem(id: String): Result<Unit> {
        return try {
            val response = api.deleteProjectExpenseItem(id)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to delete project expense item"))
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