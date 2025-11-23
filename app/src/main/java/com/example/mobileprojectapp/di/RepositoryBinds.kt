package com.example.mobileprojectapp.di

import com.example.mobileprojectapp.data.repository.AuthRepositoryImpl
import com.example.mobileprojectapp.data.repository.ProjectExpenseItemRepositoryImpl
import com.example.mobileprojectapp.data.repository.ProjectExpenseRepositoryImpl
import com.example.mobileprojectapp.data.repository.ProjectRepositoryImpl
import com.example.mobileprojectapp.data.repository.ProjectTodolistItemRepositoryImpl
import com.example.mobileprojectapp.data.repository.ProjectTodolistRepositoryImpl
import com.example.mobileprojectapp.data.repository.UserRepositoryImpl
import com.example.mobileprojectapp.domain.repository.AuthRepository
import com.example.mobileprojectapp.domain.repository.ProjectExpensesItemRepository
import com.example.mobileprojectapp.domain.repository.ProjectExpensesRepository
import com.example.mobileprojectapp.domain.repository.ProjectTodolistItemRepository
import com.example.mobileprojectapp.domain.repository.ProjectTodolistRepository
import com.example.mobileprojectapp.domain.repository.ProjectsRepository
import com.example.mobileprojectapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBinds {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl) : AuthRepository

    @Binds
    @Singleton
    abstract fun bindProjectsRepository(impl: ProjectRepositoryImpl) : ProjectsRepository

    @Binds
    @Singleton
    abstract fun bindProjectTodolistRepository(impl: ProjectTodolistRepositoryImpl) : ProjectTodolistRepository

    @Binds
    @Singleton
    abstract fun bindProjectTodolistItemRepository(impl: ProjectTodolistItemRepositoryImpl) : ProjectTodolistItemRepository

    @Binds
    @Singleton
    abstract fun bindProjectExpenseRepository(impl: ProjectExpenseRepositoryImpl) : ProjectExpensesRepository

    @Binds
    @Singleton
    abstract fun bindProjectExpenseItemRepository(impl: ProjectExpenseItemRepositoryImpl) : ProjectExpensesItemRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl) : UserRepository
}