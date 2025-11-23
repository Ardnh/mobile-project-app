package com.example.mobileprojectapp.data.repository

import com.example.mobileprojectapp.data.mapper.toDomain
import com.example.mobileprojectapp.data.remote.api.ApiService
import com.example.mobileprojectapp.domain.model.UserProfile
import com.example.mobileprojectapp.domain.repository.AuthRepository
import com.example.mobileprojectapp.domain.repository.UserRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api : ApiService) : UserRepository {
    override suspend fun getUserProfile(): Result<UserProfile> {
        return try {
            val response = api.getUserById()
            if(response.isSuccessful){
                val body = response.body()
                if(body != null && body.success && body.data != null) {
                    val result = body.data.toDomain()
                    Result.success(result)
                } else {
                    Result.failure(Exception(body?.message ?: "Failed to get user"))
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