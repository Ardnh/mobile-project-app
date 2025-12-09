package com.example.mobileprojectapp.presentation.features.projects

import androidx.lifecycle.ViewModel
import com.example.mobileprojectapp.domain.repository.ProjectsRepository
import com.example.mobileprojectapp.utils.SecureStorageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(repository: ProjectsRepository, storageManager: SecureStorageManager) : ViewModel() {

    // -----------------------------
    // UI State
    // -----------------------------

    // -----------------------------
    // API Result State
    // -----------------------------

    // -----------------------------
    // UI Event Actions
    // -----------------------------

    // -----------------------------
    // API Actions
    // -----------------------------

    suspend fun getProjectCategory(){

    }

    suspend fun getProjectsByUserId(){

    }

}