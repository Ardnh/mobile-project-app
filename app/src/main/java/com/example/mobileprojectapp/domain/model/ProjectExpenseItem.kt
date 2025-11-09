package com.example.mobileprojectapp.domain.model

data class ExpensesItem(
    val id: String,
    val projectExpensesId: String,
    val name: String,
    val amount: Long,
    val categoryName: String,
)