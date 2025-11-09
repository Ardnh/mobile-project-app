package com.example.mobileprojectapp.data.repository

import com.example.mobileprojectapp.data.mapper.toDomain
import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.data.remote.dto.LoginRequestDto
import com.example.mobileprojectapp.data.remote.dto.RegisterRequestDto
import com.example.mobileprojectapp.domain.repository.AuthRepository
import com.example.mobileprojectapp.domain.model.TokenData
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl @Inject constructor(private val api : ApiService) : AuthRepository {
    override suspend fun login(req: LoginRequestDto) : Result<TokenData> {
        return try {
            val response = api.login(req)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val token = body.data.data.toDomain()
                    Result.success(token)
                } else {
                    Result.failure(Exception(body?.message ?: "Login failed"))
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

    override suspend fun register(req: RegisterRequestDto): Result<Unit> {
        return try {
            val response = api.register(req)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(body?.message ?: "Registration failed"))
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