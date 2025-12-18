package com.example.mobileprojectapp.domain.model

import com.google.gson.annotations.SerializedName

data class TodolistItem(
    val id: String,
    val projectTodolistId: String,
    val name: String,
    val categoryName: String,
    var isCompleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: Any?,
)