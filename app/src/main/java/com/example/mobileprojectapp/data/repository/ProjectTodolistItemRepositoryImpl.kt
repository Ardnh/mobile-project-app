package com.example.mobileprojectapp.data.repository

import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistRequestDto
import com.example.mobileprojectapp.domain.model.CreateProjectTodolistRequest
import com.example.mobileprojectapp.domain.repository.ProjectTodolistRepository
import javax.inject.Inject

class ProjectTodolistItemRepositoryImpl @Inject constructor(private val api : ApiService) : ProjectTodolistRepository {
    override suspend fun createProjectTodolist(req: CreateProjectTodolistRequest): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProjectTodolist(
        id: String,
        req: UpdateProjectTodolistRequestDto
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProjectTodolist(id: String): Result<Unit> {
        TODO("Not yet implemented")
    }

}