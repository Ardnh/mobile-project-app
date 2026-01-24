package com.example.mobileprojectapp.data.repository

import com.example.mobileprojectapp.data.mapper.toDomain
import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.data.remote.dto.CreateProjectTodolistItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistItemRequestDto
import com.example.mobileprojectapp.domain.repository.ProjectTodolistItemRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.mobileprojectapp.domain.model.TodolistItem


class ProjectTodolistItemRepositoryImpl @Inject constructor(private val api : ApiService) : ProjectTodolistItemRepository {
    override suspend fun createProjectTodolistItem(todolistId: String, name: String, categoryName: String): Result<TodolistItem> {
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
                    val data = body.data.toDomain()
                    Result.success(data)
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
        projectTodolistId: String,
        name: String,
        categoryName: String,
        isCompleted: Boolean
    ): Result<TodolistItem> {
        return try {

            val req = UpdateProjectTodolistItemRequestDto(
                id = id,
                projectTodolistId = projectTodolistId,
                name = name,
                categoryName = categoryName,
                isCompleted = isCompleted,
            )

            val response = api.updateProjectTodolistItem(id, req)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success && body.data != null) {
                    val data = body.data.toDomain()
                    Result.success(data)
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