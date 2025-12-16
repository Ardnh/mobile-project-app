package com.example.mobileprojectapp.presentation.features.projects

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.model.CreateProjectRequest
import com.example.mobileprojectapp.domain.model.ProjectCategory
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.domain.model.UpdateProjectRequest
import com.example.mobileprojectapp.domain.model.UserProfile
import com.example.mobileprojectapp.domain.repository.ProjectsRepository
import com.example.mobileprojectapp.presentation.features.home.ProjectByUserIdParams
import com.example.mobileprojectapp.presentation.features.login.LoginState
import com.example.mobileprojectapp.utils.HttpAbortManager
import com.example.mobileprojectapp.utils.SecureStorageManager
import com.example.mobileprojectapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class ProjectsViewModel @Inject constructor(private val repository: ProjectsRepository, private val storage: SecureStorageManager) : ViewModel() {

    // -----------------------------
    // Instance
    // -----------------------------
    val projectMutex = Mutex()

    // -----------------------------
    // UI State
    // -----------------------------
    private val _projectByUserIdParams = MutableStateFlow<ProjectByUserIdParams>(ProjectByUserIdParams())

    // -----------------------------
    // API Result State
    // -----------------------------
    private val _searchForm = MutableStateFlow("")
    val searchForm = _searchForm.asStateFlow()

    private val _projectCategory = MutableStateFlow<State<List<ProjectCategory>>>(State.Idle)
    val projectCategory = _projectCategory.asStateFlow()

    private val _projectList = MutableStateFlow<State<List<ProjectItem>>>(State.Idle)
    val projectList = _projectList.asStateFlow()

    // -----------------------------
    // UI Event Actions
    // -----------------------------
    fun onSearchProjectChange(value: String){
        _searchForm.value = value
    }

    fun setProjectCategory(value: String) {
        _projectByUserIdParams.value = _projectByUserIdParams.value.copy(
            search = if (value == "All") "" else value
        )
        getProjectsByUserId()
    }

    // -----------------------------
    // API Actions
    // -----------------------------
    fun loadInitialData(){
        viewModelScope.launch {
            getProjectCategory()
            getProjectsByUserId()
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

    fun getProjectsByUserId(){

        val id = "get_project_by_user_id_project"
        val job = viewModelScope.launch {
            try {

                _projectList.value = State.Loading
                val userId = storage.getUserId()
                if(userId == null){
                    _projectList.value = State.Error("User Id not found!")
                    return@launch
                }
                val result = repository.getProjectsByUserId(
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

        HttpAbortManager.register(id, job)
    }

    fun createProjectByUserId(
        name: String,
        categoryName: String,
        budget: Long,
        startDate: String,
        endDate: String,
    ){

        viewModelScope.launch {
            if (!projectMutex.tryLock()) return@launch
            try {
                _projectList.value = State.Loading
                val userId = storage.getUserId()
                if(userId == null){
                    _projectList.value = State.Error("User Id not found!")
                    return@launch
                }

                val startDateSplit = if(startDate.isNotBlank()) startDate.split("T").first() else ""
                val endDateSplit = if(endDate.isNotBlank()) endDate.split("T").first() else ""
                val request = CreateProjectRequest(
                    userId = userId,
                    name = name,
                    categoryName = categoryName,
                    budget = budget,
                    startDate = startDateSplit,
                    endDate = endDateSplit,
                    isCompleted = false
                )

                Log.d("CREATE PROJECT", "$request")
                val result = repository.createProject(request)
                result.fold(
                    onSuccess = { data ->
                        getProjectsByUserId()
                    },
                    onFailure = { throwable ->
                        _projectList.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )

            } catch (e: Exception){
                _projectList.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                projectMutex.unlock()
            }

        }
    }

    fun updateProjectById(
        id: String,
        name: String,
        categoryName: String,
        budget: Long,
        startDate: String,
        endDate: String,
    ){
        viewModelScope.launch {
            if(!projectMutex.tryLock()) return@launch
            try {

                val userId = storage.getUserId()
                if(userId == null){
                    _projectList.value = State.Error("User Id not found!")
                    return@launch
                }

                val startDateSplit = if(startDate.isNotBlank()) startDate.split("T").first() else ""
                val endDateSplit = if(endDate.isNotBlank()) endDate.split("T").first() else ""
                val request = UpdateProjectRequest(
                    userId = userId,
                    name = name,
                    categoryName = categoryName,
                    budget = budget,
                    startDate = startDateSplit,
                    endDate = endDateSplit,
                    isCompleted = false
                )
                Log.d("UPDATE PROJECT", "$request")
                val result = repository.updateProjectById(id = id, req = request)
                result.fold(
                    onSuccess = { data ->
                        getProjectsByUserId()
                    },
                    onFailure = { throwable ->
                        _projectList.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )

            } catch (e: Exception){
                _projectList.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                projectMutex.unlock()
            }
        }
    }

    fun deleteProjectById(id: String) {
        viewModelScope.launch {
            if(!projectMutex.tryLock()) return@launch
            try {
                val result = repository.deleteProjectById(id = id)
                result.fold(
                    onSuccess = { data ->
                        getProjectsByUserId()
                    },
                    onFailure = { throwable ->
                        _projectList.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch (e: Exception){
                _projectList.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                projectMutex.unlock()
            }
        }
    }

}