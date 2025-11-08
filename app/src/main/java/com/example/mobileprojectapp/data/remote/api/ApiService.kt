package com.example.mobileprojectapp.data.remote.api

import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseItemRequest
import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseRequest
import com.example.mobileprojectapp.data.remote.dto.CreateProjectRequest
import com.example.mobileprojectapp.data.remote.dto.CreateProjectTodolistItemRequest
import com.example.mobileprojectapp.data.remote.dto.CreateProjectTodolistRequest
import com.example.mobileprojectapp.data.remote.dto.CreateUserRequest
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectRequest
import com.example.mobileprojectapp.data.remote.dto.LoginRequest
import com.example.mobileprojectapp.data.remote.dto.LoginResponse
import com.example.mobileprojectapp.data.remote.dto.ProjectByUserId
import com.example.mobileprojectapp.data.remote.dto.ProjectCategoryByUserId
import com.example.mobileprojectapp.data.remote.dto.ProjectSummaryByUserId
import com.example.mobileprojectapp.data.remote.dto.ProjectsById
import com.example.mobileprojectapp.data.remote.dto.RegisterRequest
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseItemRequest
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseRequest
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistItemRequest
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectTodolistRequest
import com.example.mobileprojectapp.data.remote.dto.UpdateUserRequest
import com.example.mobileprojectapp.data.remote.dto.UserByTokenResponse
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
        @Body request: LoginRequest
    ): Response<BaseResponse<LoginResponse>>

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
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
    suspend fun getUserById(
        @Path("id") id: String
    ): Response<BaseResponse<UserByTokenResponse>>

    @POST("users")
    suspend fun createUser(
        @Body request: CreateUserRequest
    ): Response<BaseResponse<Unit>>

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body request: UpdateUserRequest
    ): Response<BaseResponse<Unit>>

    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Path("id") id: String
    ): Response<BaseResponse<Unit>>

    // ========================================
    // PROJECT ROUTES (Protected)
    // ========================================
    @GET("projects/by-id/{id}")
    suspend fun getProjectById(
        @Path("id") id: String
    ): Response<BaseResponse<ProjectsById>>

    @GET("projects/user/{user_id}")
    suspend fun getProjectsByUserId(
        @Path("user_id") userId: String,
        @Query("status") status: String? = null,
        @Query("sort") sort: String? = null
    ): Response<BaseResponse<List<ProjectByUserId>>>

    @GET("projects/user/{user_id}/category")
    suspend fun getProjectCategoryByUserId(
        @Path("user_id") userId: String
    ): Response<BaseResponse<List<ProjectCategoryByUserId>>>

    @GET("projects/user/{user_id}/summary")
    suspend fun getAllProjectSummaryByUserId(
        @Path("user_id") userId: String
    ): Response<BaseResponse<ProjectSummaryByUserId>>

    @POST("projects")
    suspend fun createProject(
        @Body request: CreateProjectRequest
    ): Response<BaseResponse<Unit>>

    @PUT("projects/{id}")
    suspend fun updateProject(
        @Path("id") id: String,
        @Body request: UpdateProjectRequest
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
        @Body request: CreateProjectExpenseRequest
    ): Response<BaseResponse<Unit>>

    @PUT("project-expenses/{id}")
    suspend fun updateProjectExpense(
        @Path("id") id: String,
        @Body request: UpdateProjectExpenseRequest
    ): Response<BaseResponse<Unit>>

    @DELETE("project-expenses/{id}")
    suspend fun deleteProjectExpense(
        @Path("id") id: String
    ): Response<BaseResponse<Unit>>

    // ========================================
    // PROJECT EXPENSES ITEM ROUTES (Protected)
    // ========================================

    @POST("project-expenses-item")
    suspend fun createProjectExpenseItem(
        @Body request: CreateProjectExpenseItemRequest
    ): Response<BaseResponse<Unit>>

    @PUT("project-expenses-item/{id}")
    suspend fun updateProjectExpenseItem(
        @Path("id") id: String,
        @Body request: UpdateProjectExpenseItemRequest
    ): Response<BaseResponse<Unit>>

    @DELETE("project-expenses-item/{id}")
    suspend fun deleteProjectExpenseItem(
        @Path("id") id: String
    ): Response<BaseResponse<Unit>>

    // ========================================
    // PROJECT TODOLIST ROUTES (Protected)
    // ========================================

    @POST("project-todolist")
    suspend fun createProjectTodolist(
        @Body request: CreateProjectTodolistRequest
    ): Response<BaseResponse<Unit>>

    @PUT("project-todolist/{id}")
    suspend fun updateProjectTodolist(
        @Path("id") id: String,
        @Body request: UpdateProjectTodolistRequest
    ): Response<BaseResponse<Unit>>

    @DELETE("project-todolist/{id}")
    suspend fun deleteProjectTodolist(
        @Path("id") id: String
    ): Response<BaseResponse<Unit>>

    // ========================================
    // PROJECT TODOLIST ITEM ROUTES (Protected)
    // ========================================

    @POST("project-todolist-item")
    suspend fun createProjectTodolistItem(
        @Body request: CreateProjectTodolistItemRequest
    ): Response<BaseResponse<Unit>>

    @PUT("project-todolist-item/{id}")
    suspend fun updateProjectTodolistItem(
        @Path("id") id: String,
        @Body request: UpdateProjectTodolistItemRequest
    ): Response<BaseResponse<Unit>>

    @DELETE("project-todolist-item/{id}")
    suspend fun deleteProjectTodolistItem(
        @Path("id") id: String
    ): Response<BaseResponse<Unit>>
}
