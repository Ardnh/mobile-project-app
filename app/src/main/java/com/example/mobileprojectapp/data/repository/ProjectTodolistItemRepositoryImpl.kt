package com.example.mobileprojectapp.data.repository

import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.data.remote.dto.CreateProjectTodolistItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistRequestDto
import com.example.mobileprojectapp.domain.model.CreateProjectTodolistRequest
import com.example.mobileprojectapp.domain.repository.ProjectTodolistItemRepository
import com.example.mobileprojectapp.domain.repository.ProjectTodolistRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProjectTodolistItemRepositoryImpl @Inject constructor(private val api : ApiService) : ProjectTodolistItemRepository {
    override suspend fun createProjectTodolistItem(todolistId: String, name: String, categoryName: String): Result<Unit> {
        return try {

            val req = CreateProjectTodolistItemRequestDto(
                projectTodolistId = todolistId,
                name = name,
                categoryName = categoryName,
                isCompleted = false
            )
            val response = api.createProjectTodolistItem(req)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success && body.data != null) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to create project todolist item"))
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

    override suspend fun updateProjectTodolistItem(
        id: String,
        req: UpdateProjectTodolistItemRequestDto
    ): Result<Unit> {
        return try {
            val response = api.updateProjectTodolistItem(id, req)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to update project todolist item"))
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

    override suspend fun deleteProjectTodolistItem(id: String): Result<Unit> {
        return try {
            val response = api.deleteProjectTodolistItem(id)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to delete project todolist item"))
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