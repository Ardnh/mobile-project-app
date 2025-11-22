package com.example.mobileprojectapp.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.model.ProjectByUserId
import com.example.mobileprojectapp.domain.model.ProjectCategory
import com.example.mobileprojectapp.domain.model.ProjectSummary
import com.example.mobileprojectapp.domain.repository.ProjectsRepository
import com.example.mobileprojectapp.presentation.navigation.NavigationEvent
import com.example.mobileprojectapp.utils.SecureStorageManager
import com.example.mobileprojectapp.utils.State
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val projectRepository: ProjectsRepository, private val storage: SecureStorageManager) : ViewModel() {

    // -----------------------------
    // UI State
    // -----------------------------
    private val _projectByUserIdParamsParams = MutableStateFlow<ProjectByUserIdParams>(ProjectByUserIdParams())

    // SharedFlow untuk navigation events (one-time events)
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    // -----------------------------
    // API Result State
    // -----------------------------
    private val _projectSummary = MutableStateFlow<State<ProjectSummary>>(State.Idle)
    val projectSummary = _projectSummary.asStateFlow()

    private val _projectCategory = MutableStateFlow<State<List<ProjectCategory>>>(State.Idle)
    val projectCategory = _projectCategory.asStateFlow()

    private val _projectList = MutableStateFlow<State<List<ProjectByUserId>>>(State.Idle)
    val projectList = _projectList.asStateFlow()

    // -----------------------------
    // UI Event Actions
    // -----------------------------


    // -----------------------------
    // API Actions
    // -----------------------------

    fun getSummaryByUserId(){
        viewModelScope.launch {

            try {

                _projectSummary.value = State.Loading

                val userId = storage.getUserId()
                if(userId == null){
                    _projectSummary.value = State.Error("User Id not found!")
                    return@launch
                }
                val result = projectRepository.getProjectSummaryByUserId(userId = userId)
                result.fold(
                    onSuccess = { data ->
                        _projectSummary.value = State.Success(data)
                    },
                    onFailure = { throwable ->
                        _projectSummary.value = State.Error(throwable.message ?: "Unkown Error")
                    }
                )

            } catch (e: Exception){
                _projectSummary.value = State.Error(e.message ?: "Unkown Error")
            }

        }
    }

    fun getProjectCategory(){
        viewModelScope.launch {
            try {

                _projectCategory.value = State.Loading

                val userId = storage.getUserId()
                if(userId == null){
                    _projectCategory.value = State.Error("User Id not found!")
                    return@launch
                }
                val result = projectRepository.getProjectCategory(userId = userId)
                result.fold(
                    onSuccess = { data ->
                        _projectCategory.value = State.Success(data)
                    },
                    onFailure = { throwable ->
                        _projectCategory.value = State.Error(throwable.message ?: "Unkown Error")
                    }
                )

            } catch (e: Exception) {
                _projectCategory.value = State.Error(e.message ?: "Unkown Error")
            }

        }
    }

    fun getProjectsByUserId(){
        viewModelScope.launch {

            try {
                _projectList.value = State.Loading
                val userId = storage.getUserId()
                if(userId == null){
                    _projectList.value = State.Error("User Id not found!")
                    return@launch
                }
                val result = projectRepository.getProjectsByUserId(
                    userId = userId,
                    param = _projectByUserIdParamsParams.value
                )
                result.fold(
                    onSuccess = { data ->
                        _projectList.value = State.Success(data)
                    },
                    onFailure = { throwable ->
                        _projectList.value = State.Error(throwable.message ?: "Unkown Error")
                    }
                )

            } catch (e: Exception){
                _projectList.value = State.Error(e.message ?: "Unkown Error")
            }

        }
    }
}