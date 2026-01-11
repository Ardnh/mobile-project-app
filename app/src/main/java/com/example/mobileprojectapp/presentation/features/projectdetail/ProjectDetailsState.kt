package com.example.mobileprojectapp.presentation.features.projectdetail

data class UpdateCategoryTodolist(
    val id: String,
    val projectId: String,
    val name: String
)

data class DeleteCategoryInfo(
    val id: String,
    val name: String
)