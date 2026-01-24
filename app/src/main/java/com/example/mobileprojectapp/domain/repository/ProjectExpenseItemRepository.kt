package com.example.mobileprojectapp.domain.repository

import com.example.mobileprojectapp.data.remote.dto.CreateProjectExpenseItemRequestDto
import com.example.mobileprojectapp.data.remote.dto.UpdateProjectExpenseItemRequestDto
import com.example.mobileprojectapp.domain.model.ExpensesItem

interface ProjectExpensesItemRepository {
    suspend fun createProjectExpensesItem(projectExpensesId: String, name: String, amount: Long, categoryName: String) : Result<ExpensesItem>
    suspend fun updateProjectExpensesItem(id: String, projectExpeseId: String,  name: String, amount: Long, categoryName: String) : Result<ExpensesItem>
    suspend fun deleteProjectExpensesItem(id: String) : Result<Unit>
}