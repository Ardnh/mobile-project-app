package com.example.mobileprojectapp.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.model.ProjectCategory
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.domain.model.ProjectSummary
import com.example.mobileprojectapp.domain.model.UserProfile
import com.example.mobileprojectapp.domain.repository.ProjectsRepository
import com.example.mobileprojectapp.presentation.navigation.NavigationEvent
import com.example.mobileprojectapp.utils.SecureStorageManager
import com.example.mobileprojectapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val projectRepository: ProjectsRepository, private val storage: SecureStorageManager) : ViewModel() {

    // -----------------------------
    // UI State
    // -----------------------------
    private val _projectByUserIdParams = MutableStateFlow<ProjectByUserIdParams>(ProjectByUserIdParams())

    // SharedFlow untuk navigation events (one-time events)
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    // -----------------------------
    // API Result State
    // -----------------------------
    private val _userProfile = MutableStateFlow<State<UserProfile>>(State.Idle)
    val userProfile = _userProfile.asStateFlow()

    private val _projectSummary = MutableStateFlow<State<ProjectSummary>>(State.Idle)
    val projectSummary = _projectSummary.asStateFlow()

    private val _projectCategory = MutableStateFlow<State<List<ProjectCategory>>>(State.Idle)
    val projectCategory = _projectCategory.asStateFlow()

    private val _projectList = MutableStateFlow<State<List<ProjectItem>>>(State.Idle)
    val projectList = _projectList.asStateFlow()

    // -----------------------------
    // UI Event Actions
    // -----------------------------


    // -----------------------------
    // API Actions
    // -----------------------------
    fun loadInitialData(){
        viewModelScope.launch {
            val userId = storage.getUserId()
            val username = storage.getUsername()

            if (userId.isNullOrEmpty() || username.isNullOrEmpty()) {
                _userProfile.value = State.Error("User credentials not found")
                return@launch
            }

            _userProfile.value = State.Success(UserProfile(userId, username))

            launch{ getSummaryByUserId() }
            launch{ getProjectCategory() }
            launch{ getProjectsByUserId() }
        }
    }

    suspend fun getSummaryByUserId(){
        try {
            _projectSummary.value = State.Loading

            val userId = storage.getUserId()
            if(userId == null){
                _projectSummary.value = State.Error("User Id not found!")
                return
            }
            val result = projectRepository.getProjectSummaryByUserId(userId = userId)
            result.fold(
                onSuccess = { data ->
                    _projectSummary.value = State.Success(data)
                },
                onFailure = { throwable ->
                    _projectSummary.value = State.Error(throwable.message ?: "Unknown Error")
                }
            )

        } catch (e: Exception){
            _projectSummary.value = State.Error(e.message ?: "Unknown Error")
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
            val result = projectRepository.getProjectCategory(userId = userId)
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

    suspend fun getProjectsByUserId(){
        try {
            _projectList.value = State.Loading
            val userId = storage.getUserId()
            if(userId == null){
                _projectList.value = State.Error("User Id not found!")
                return
            }
            val result = projectRepository.getProjectsByUserId(
                userId = userId,
                param = _projectByUserIdParams.value
            )
            result.fold(
                onSuccess = { data ->
                    _projectList.value = State.Success(data)
                },
                onFailure = { throwable ->
                    _projectList.value = State.Error(throwable.message ?: "Unknown Error")
                }
            )

        } catch (e: Exception){
            _projectList.value = State.Error(e.message ?: "Unknown Error")
        }
    }
}