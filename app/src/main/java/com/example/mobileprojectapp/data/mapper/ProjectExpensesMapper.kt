package com.example.mobileprojectapp.data.mapper

import com.example.mobileprojectapp.data.remote.dto.ProjectExpenseDto
import com.example.mobileprojectapp.domain.model.ProjectExpense

fun ProjectExpenseDto.toDomain() : ProjectExpense {

    val projectExpensesItem = expensesItem.map { it.toDomain() }

    return ProjectExpense(
        id = id,
        projectId = projectId,
        name = name,
        expensesUsed = expensesUsed,
        expensesItem = projectExpensesItem,
    )
}