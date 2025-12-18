package com.example.mobileprojectapp.presentation.features.projectdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.model.ProjectById
import com.example.mobileprojectapp.domain.model.ProjectCategory
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.domain.model.UpdateProjectRequest
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

    private val _updateProjectState = MutableStateFlow<State<Unit>>(State.Idle)
    val updateProjectState = _updateProjectState.asStateFlow()

    // -----------------------------
    // API Result State
    // -----------------------------
    private val _projectDetail = MutableStateFlow<State<ProjectById>>(State.Idle)
    val projectDetail = _projectDetail.asStateFlow()
    private val _projectCategory = MutableStateFlow<State<List<ProjectCategory>>>(State.Idle)
    val projectCategory = _projectCategory.asStateFlow()

    // -----------------------------
    // UI Event Actions
    // -----------------------------

    // -----------------------------
    // API Actions
    // -----------------------------
    fun getProjectsById(projectId: String){

        viewModelScope.launch {
            try {

                Log.d("GET DETAIL", "RUN getProjectsById(projectId: String)")
                if (_projectDetail.value is State.Loading) {
                    Log.w("GET DETAIL", "Already processing, skipping")
                    return@launch
                }

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
    }

    suspend fun getProjectCategory(){
        try {
            _projectCategory.value = State.Loading

            val userId = storage.getUserId()
            if(userId == null){
                _projectCategory.value = State.Error("User Id not found!")
                return
            }
            val result = repository.getProjectCategory(userId = userId)
            result.fold(
                onSuccess = { data ->
                    _projectCategory.value = State.Success(data)
                },
                onFailure = { throwable ->
                    _projectCategory.value = State.Error(throwable.message ?: "Unknown Error")
                }
            )

        } catch (e: Exception) {
            _projectCategory.value = State.Error(e.message ?: "Unknown Error")
        }
    }

    fun updateProjectById(
        id: String?,
        userId: String?,
        name: String,
        budget: String,
        startDate: String,
        endDate: String,
        category: String
    ){

        viewModelScope.launch {
            try {

                if (_updateProjectState.value is State.Loading) {
                    Log.w("UPDATE PROJECT", "Already processing, skipping")
                    return@launch
                }

                _updateProjectState.value = State.Loading
                if(id == null || userId == null){
                    Log.w("UPDATE PROJECT", "Project ID OR USERID NULL")
                    return@launch
                }

                val request = UpdateProjectRequest(
                    userId = userId,
                    name = name,
                    categoryName = category,
                    budget = budget.toLong(),
                    startDate = startDate,
                    endDate = endDate,
                    isCompleted = false
                )

                val result = repository.updateProjectById(id, request)
                result.fold(
                    onSuccess = { data ->
                        _updateProjectState.value = State.Success(data)
                    },
                    onFailure = { throwable ->
                        _updateProjectState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )

            } catch (e: Exception){
                _updateProjectState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                if(id !== null){
                    getProjectsById(id)
                }
            }
        }

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