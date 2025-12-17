package com.example.mobileprojectapp.presentation.features.projectdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.model.ProjectById
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.domain.repository.ProjectExpensesItemRepository
import com.example.mobileprojectapp.domain.repository.ProjectExpensesRepository
import com.example.mobileprojectapp.domain.repository.ProjectTodolistItemRepository
import com.example.mobileprojectapp.domain.repository.ProjectTodolistRepository
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
class ProjectDetailsViewModel @Inject constructor(
    private val repository: ProjectsRepository,
    private val projectTodolistRepository: ProjectTodolistRepository,
    private val projectTodolistItemRepository: ProjectTodolistItemRepository,
    private val projectExpensesRepository: ProjectExpensesRepository,
    private val projectExpensesItemRepository: ProjectExpensesItemRepository,
    private val storage: SecureStorageManager
) : ViewModel() {

    // -----------------------------
    // UI State
    // -----------------------------
    private val _deleteProjectState = MutableStateFlow<State<Unit>>(State.Idle)
    val deleteProjectState = _deleteProjectState.asStateFlow()

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

    fun updateProjectTodolistById(){

    }

    fun updateTodolistItemById(){

    }

    fun deleteProjectById(projectId: String?){
        viewModelScope.launch {
            try {

                if (_deleteProjectState.value is State.Loading) {
                    Log.w("Delete PROJECT", "Already processing, skipping")
                    return@launch
                }

                _deleteProjectState.value = State.Loading

                if(projectId == null){
                    Log.w("Delete PROJECT", "Project ID NULL")
                    return@launch
                }

                val result = repository.deleteProjectById(projectId)
                result.fold(
                    onSuccess = { data ->

                    },
                    onFailure = { throwable ->
                        _deleteProjectState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )

            } catch (e: Exception){
                _deleteProjectState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                _deleteProjectState.value = State.Idle
            }
        }
    }

    fun deleteCategoryTodolistById(projectId: String){
        viewModelScope.launch {
            try {

                if (_projectDetail.value is State.Loading) {
                    Log.w("CREATE PROJECT", "Already processing, skipping")
                    return@launch
                }

                _projectDetail.value = State.Loading
                val result = repository.deleteProjectById(projectId)
                result.fold(
                    onSuccess = { data ->

                    },
                    onFailure = { throwable ->
                        _projectDetail.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )

            } catch (e: Exception){
                _projectDetail.value = State.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun deleteTodolistItemById(projectId: String){
        viewModelScope.launch {
            try {

                if (_projectDetail.value is State.Loading) {
                    Log.w("CREATE PROJECT", "Already processing, skipping")
                    return@launch
                }

                _projectDetail.value = State.Loading
                val result = repository.deleteProjectById(projectId)
                result.fold(
                    onSuccess = { data ->

                    },
                    onFailure = { throwable ->
                        _projectDetail.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )

            } catch (e: Exception){
                _projectDetail.value = State.Error(e.message ?: "Unknown Error")
            }
        }
    }
}