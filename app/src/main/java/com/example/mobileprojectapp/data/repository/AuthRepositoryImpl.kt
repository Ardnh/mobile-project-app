package com.example.mobileprojectapp.data.repository

import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.data.remote.dto.LoginRequest
import com.example.mobileprojectapp.data.remote.dto.RegisterRequest
import com.example.mobileprojectapp.domain.repository.AuthRepository
import javax.inject.Inject
import com.example.mobileprojectapp.data.remote.dto.TokenData
import retrofit2.HttpException
import java.io.IOException


class AuthRepositoryImpl @Inject constructor(private val api : ApiService) : AuthRepository {
    override suspend fun login(req: LoginRequest) : Result<TokenData> {
        return try {
            val response = api.login(req)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    Result.success(body.data.data)
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

    override suspend fun register(req: RegisterRequest): Result<Unit> {
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