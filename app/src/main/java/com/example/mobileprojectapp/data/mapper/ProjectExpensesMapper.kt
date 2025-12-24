package com.example.mobileprojectapp.data.mapper

import com.example.mobileprojectapp.data.remote.dto.ProjectExpenseDto
import com.example.mobileprojectapp.domain.model.ProjectExpense
import com.example.mobileprojectapp.utils.toNumberFormat

fun ProjectExpenseDto.toDomain() : ProjectExpense {

    val projectExpensesItem = expensesItem.map { it.toDomain() }
    return ProjectExpense(
        id = id,
        projectId = projectId,
        name = name,
        expensesUsed = expensesUsed.toNumberFormat(),
        expensesItem = projectExpensesItem,
    )
}