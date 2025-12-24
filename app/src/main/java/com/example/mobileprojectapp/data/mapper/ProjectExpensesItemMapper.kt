package com.example.mobileprojectapp.data.mapper

import com.example.mobileprojectapp.data.remote.dto.ExpensesItemDto
import com.example.mobileprojectapp.domain.model.ExpensesItem
import com.example.mobileprojectapp.utils.toNumberFormat

fun ExpensesItemDto.toDomain() : ExpensesItem {
    return ExpensesItem(
        id = id,
        projectExpensesId = projectExpensesId,
        name = name,
        amount = amount.toNumberFormat(),
        categoryName = categoryName
    )
}