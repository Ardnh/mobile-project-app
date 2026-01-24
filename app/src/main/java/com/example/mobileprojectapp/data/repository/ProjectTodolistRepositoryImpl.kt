package com.example.mobileprojectapp.data.repository

import com.example.mobileprojectapp.data.mapper.toDomain
import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.data.remote.dto.CreateProjectTodolistRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistRequestDto
import com.example.mobileprojectapp.domain.model.CreateProjectTodolistRequest
import com.example.mobileprojectapp.domain.model.ProjectById
import com.example.mobileprojectapp.domain.model.ProjectTodolist
import com.example.mobileprojectapp.domain.repository.ProjectTodolistRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProjectTodolistRepositoryImpl @Inject constructor(private val api : ApiService) : ProjectTodolistRepository{
    override suspend fun createProjectTodolist(projectId: String, name: String): Result<ProjectTodolist> {
        return try {
            val req = CreateProjectTodolistRequestDto(
                projectId = projectId,
                name = name
            )
            val response = api.createProjectTodolist(req)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success && body.data != null) {
                    val data = body.data.toDomain()
                    Result.success(data)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to create project todolist"))
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

    override suspend fun updateProjectTodolist(id: String, projectId: String, name: String): Result<ProjectTodolist> {
        return try {

            val req = UpdateProjectTodolistRequestDto(
                id = id,
                projectId = projectId,
                name = name
            )

            val response = api.updateProjectTodolist(id, req)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success && body.data != null) {
                    val data = body.data.toDomain()
                    Result.success(data)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to update project todolist"))
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

    override suspend fun deleteProjectTodolist(id: String): Result<Unit> {
        return try {
            val response = api.deleteProjectTodolist(id)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to delete project todolist"))
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