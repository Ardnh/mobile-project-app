package com.example.mobileprojectapp.presentation.features.projectdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.model.ProjectById
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.domain.repository.ProjectsRepository
import com.example.mobileprojectapp.presentation.features.home.ProjectByUserIdParams
import com.example.mobileprojectapp.utils.HttpAbortManager
import com.example.mobileprojectapp.utils.SecureStorageManager
import com.example.mobileprojectapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDetailsViewModel @Inject constructor(private val repository: ProjectsRepository, private val storage: SecureStorageManager) : ViewModel() {

    // -----------------------------
    // UI State
    // -----------------------------

    // -----------------------------
    // API Result State
    // -----------------------------
    private val _projectDetail = MutableStateFlow<State<ProjectById>>(State.Idle)
    val projectDetail = _projectDetail.asStateFlow()

    // -----------------------------
    // UI Event Actions
    // -----------------------------

    // -----------------------------
    // API Actions
    // -----------------------------
    fun getProjectsByUserId(projectId: String){

        val id = "get_project_by_id_project"
        val job = viewModelScope.launch {
            try {

                _projectDetail.value = State.Loading
                val result = repository.getProjectById(projectId)
                result.fold(
                    onSuccess = { data ->
                        _projectDetail.value = State.Success(data)
                    },
                    onFailure = { throwable ->
                        _projectDetail.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )

            } catch (e: Exception){
                _projectDetail.value = State.Error(e.message ?: "Unknown Error")
            }
        }

        HttpAbortManager.register(id, job)
    }
}