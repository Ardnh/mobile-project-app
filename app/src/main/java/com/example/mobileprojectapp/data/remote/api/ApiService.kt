package com.example.mobileprojectapp.data.remote.api

import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseRequestDto
import com.example.mobileprojectapp.data.remote.dto.CreateProjectRequestDto
import com.example.mobileprojectapp.data.remote.dto.CreateProjectTodolistItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.CreateProjectTodolistRequestDto
import com.example.mobileprojectapp.data.remote.dto.CreateUserRequestDto
import com.example.mobileprojectapp.data.remote.dto.ExpensesItemDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectRequestDto
import com.example.mobileprojectapp.data.remote.dto.LoginRequestDto
import com.example.mobileprojectapp.data.remote.dto.LoginResponseDto
import com.example.mobileprojectapp.data.remote.dto.ProjectByIdDto
import com.example.mobileprojectapp.data.remote.dto.ProjectByUserIdDto
import com.example.mobileprojectapp.data.remote.dto.ProjectCategoryByUserIdDto
import com.example.mobileprojectapp.data.remote.dto.ProjectExpenseDto
import com.example.mobileprojectapp.data.remote.dto.ProjectSummaryByUserIdDto
import com.example.mobileprojectapp.data.remote.dto.ProjectTodolistDto
import com.example.mobileprojectapp.data.remote.dto.RegisterRequestDto
import com.example.mobileprojectapp.data.remote.dto.TodolistItemDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateUserRequestDto
import com.example.mobileprojectapp.data.remote.dto.UserByTokenResponseDto
import com.example.mobileprojectapp.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // ========================================
    // PUBLIC ROUTES (No Authentication)
    // ========================================
    @POST("login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<BaseResponse<LoginResponseDto>>

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequestDto
    ): Response<BaseResponse<Unit>>

    // ========================================
    // USER ROUTES (Protected)
    // ========================================

//    @GET("users/all")
//    suspend fun getAllUsers(
//        @Query("page") page: Int? = null,
//        @Query("limit") limit: Int? = null,
//        @Query("search") search: String? = null
//    ): Response<BaseResponse<List<UserResponse>>>

    @GET("users")
    suspend fun getUserById(): Response<BaseResponse<UserByTokenResponseDto>>

    // ========================================
    // PROJECT ROUTES (Protected)
    // ========================================
    @GET("projects/by-id/{id}")
    suspend fun getProjectById(
        @Path("id") id: String
    ): Response<BaseResponse<ProjectByIdDto>>

    @GET("projects/user/{user_id}")
    suspend fun getProjectsByUserId(
        @Path("user_id") userId: String,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null,
        @Query("sort_by") sortBy: String? = null,
        @Query("sort_order") sortOrder: String? = null,
        @Query("search") search: String? = null
    ): Response<BaseResponse<List<ProjectByUserIdDto>>>

    @GET("projects/user/{user_id}/category")
    suspend fun getProjectCategoryByUserId(
        @Path("user_id") userId: String
    ): Response<BaseResponse<List<ProjectCategoryByUserIdDto>>>

    @GET("projects/user/{user_id}/summary")
    suspend fun getAllProjectSummaryByUserId(
        @Path("user_id") userId: String
    ): Response<BaseResponse<ProjectSummaryByUserIdDto>>

    @POST("projects")
    suspend fun createProject(
        @Body request: CreateProjectRequestDto
    ): Response<BaseResponse<Unit>>

    @PUT("projects/{id}")
    suspend fun updateProject(
        @Path("id") id: String,
        @Body request: UpdateProjectRequestDto
    ): Response<BaseResponse<Unit>>

    @DELETE("projects/{id}")
    suspend fun deleteProject(
        @Path("id") id: String
    ): Response<BaseResponse<Unit>>

    // ========================================
    // PROJECT EXPENSES ROUTES (Protected)
    // ========================================
    @POST("project-expenses")
    suspend fun createProjectExpense(
        @Body request: CreateProjectExpenseRequestDto
    ): Response<BaseResponse<ProjectExpenseDto>>

    @PUT("project-expenses/{id}")
    suspend fun updateProjectExpense(
        @Path("id") id: String,
        @Body request: UpdateProjectExpenseRequestDto
    ): Response<BaseResponse<ProjectExpenseDto>>

    @DELETE("project-expenses/{id}")
    suspend fun deleteProjectExpense(
        @Path("id") id: String
    ): Response<BaseResponse<Unit>>

    // ========================================
    // PROJECT EXPENSES ITEM ROUTES (Protected)
    // ========================================
    @POST("project-expenses-item")
    suspend fun createProjectExpenseItem(
        @Body request: CreateProjectExpenseItemRequestDto
    ): Response<BaseResponse<ExpensesItemDto>>

    @PUT("project-expenses-item/{id}")
    suspend fun updateProjectExpenseItem(
        @Path("id") id: String,
        @Body request: UpdateProjectExpenseItemRequestDto
    ): Response<BaseResponse<ExpensesItemDto>>

    @DELETE("project-expenses-item/{id}")
    suspend fun deleteProjectExpenseItem(
        @Path("id") id: String
    ): Response<BaseResponse<Unit>>

    // ========================================
    // PROJECT TODOLIST ROUTES (Protected)
    // ========================================
    @POST("project-todolist")
    suspend fun createProjectTodolist(
        @Body request: CreateProjectTodolistRequestDto
    ): Response<BaseResponse<ProjectTodolistDto>>

    @PUT("project-todolist/{id}")
    suspend fun updateProjectTodolist(
        @Path("id") id: String,
        @Body request: UpdateProjectTodolistRequestDto
    ): Response<BaseResponse<ProjectTodolistDto>>

    @DELETE("project-todolist/{id}")
    suspend fun deleteProjectTodolist(
        @Path("id") id: String
    ): Response<BaseResponse<Unit>>

    // ========================================
    // PROJECT TODOLIST ITEM ROUTES (Protected)
    // ========================================
    @POST("project-todolist-item")
    suspend fun createProjectTodolistItem(
        @Body request: CreateProjectTodolistItemRequestDto
    ): Response<BaseResponse<TodolistItemDto>>

    @PUT("project-todolist-item/{id}")
    suspend fun updateProjectTodolistItem(
        @Path("id") id: String,
        @Body request: UpdateProjectTodolistItemRequestDto
    ): Response<BaseResponse<TodolistItemDto>>

    @DELETE("project-todolist-item/{id}")
    suspend fun deleteProjectTodolistItem(
        @Path("id") id: String
    ): Response<BaseResponse<Unit>>
}
