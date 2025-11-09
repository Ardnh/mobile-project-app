package com.example.mobileprojectapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mobileprojectapp.data.mapper.toDomain
import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.data.remote.dto.CreateProjectRequestDto
import com.example.mobileprojectapp.data.remote.dto.ProjectByUserIdParamsDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectRequestDto
import com.example.mobileprojectapp.domain.model.ProjectByUserId
import com.example.mobileprojectapp.domain.model.ProjectCategory
import com.example.mobileprojectapp.domain.model.ProjectSummary
import com.example.mobileprojectapp.domain.model.ProjectById
import com.example.mobileprojectapp.domain.repository.ProjectsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(private val api : ApiService) : ProjectsRepository {
    override suspend fun getProjectCategory(userId: String) : Result<List<ProjectCategory>> {
        return try {
            val response = api.getProjectCategoryByUserId(userId)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success && body.data != null) {
                    val result = body.data.map { it -> it.toDomain() }
                    Result.success(result)
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getProjectsByUserId(userId: String, param: ProjectByUserIdParamsDto) : Result<List<ProjectByUserId>> {
        return try {
            val response = api.getProjectsByUserId(
                userId = userId,
                limit = param.limit,
                page = param.page,
                sortBy = param.sortBy,
                sortOrder = param.sortOrder,
                search = param.search
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val result = body.data.map { it -> it.toDomain() }
                    Result.success(result)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to get projects"))
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

    override suspend fun getProjectSummaryByUserId(userId: String): Result<ProjectSummary> {
        return try {
            val response = api.getAllProjectSummaryByUserId(userId)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val result = body.data.toDomain()
                    Result.success(result)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to get project summary"))
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

    override suspend fun getProjectById(id: String): Result<ProjectById> {
        return try {
            val response = api.getProjectById(id)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val result = body.data.toDomain()
                    Result.success(result)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to get project"))
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

    override suspend fun createProject(req: CreateProjectRequestDto): Result<Unit> {
        return try {
            val response = api.createProject(req)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to create project"))
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

    override suspend fun updateProjectById(id: String, req: UpdateProjectRequestDto): Result<Unit> {
        return try {
            val response = api.updateProject(id, req)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to update project"))
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

    override suspend fun deleteProjectById(id: String): Result<Unit> {
        return try {
            val response = api.deleteProject(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to delete project"))
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