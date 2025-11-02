package com.example.mobileprojectapp.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: T?,

    @SerializedName("errors")
    val errors: List<String>? = null
)