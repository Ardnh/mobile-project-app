package com.example.mobileprojectapp.domain.model
data class ProjectExpense(
    val id: String,
    val projectId: String,
    val name: String,
    val expensesUsed: String,
    val expensesItem: List<ExpensesItem>,
)