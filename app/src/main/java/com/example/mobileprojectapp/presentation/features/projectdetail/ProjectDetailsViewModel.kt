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
    private val _createTodolistState = MutableStateFlow<State<Unit>>(State.Idle)
    val createTodolistState = _createTodolistState.asStateFlow()
    private val _createTodolistItemState = MutableStateFlow<State<Unit>>(State.Idle)
    val createTodolistItemState = _createTodolistItemState.asStateFlow()

    private val _createExpensesState = MutableStateFlow<State<Unit>>(State.Idle)
    val createExpensesState = _createExpensesState.asStateFlow()
    private val _createExpenseItemState = MutableStateFlow<State<Unit>>(State.Idle)
    val createExpenseItemState = _createExpenseItemState.asStateFlow()


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

    fun createProjectTodolist(projectId: String?, name: String){
        viewModelScope.launch {
            try {

                if (_createTodolistState.value is State.Loading) {
                    Log.w("CREATE PROJECT TODOLIST", "Already processing, skipping")
                    return@launch
                }

                if(projectId == null) {
                    _createTodolistState.value = State.Error("Project ID id null")
                    return@launch
                }

                val result = projectTodolistRepository.createProjectTodolist(projectId = projectId, name = name)
                result.fold(
                    onSuccess = {
                        _createTodolistState.value = State.Success(Unit)
                    },
                    onFailure = { throwable ->
                        _createTodolistState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch (e: Exception) {
                _createTodolistState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                if(projectId !== null){
                    getProjectsById(projectId)
                }
            }
        }
    }

    fun createProjectTodolistItem(projectId: String?, todolistId: String, categoryName: String, name: String){
        viewModelScope.launch {
            try {
                if (_createTodolistItemState.value is State.Loading) {
                    Log.w("CREATE PROJECT TODOLIST", "Already processing, skipping")
                    return@launch
                }

                Log.d("CREATE TODOLIST ITEM", "project id: $projectId")
                Log.d("CREATE TODOLIST ITEM", "todolist id: $todolistId")
                Log.d("CREATE TODOLIST ITEM", "category name: $categoryName")
                Log.d("CREATE TODOLIST ITEM", "name: $name")

                val result = projectTodolistItemRepository.createProjectTodolistItem(
                    todolistId = todolistId,
                    name = name,
                    categoryName = categoryName
                )
                result.fold(
                    onSuccess = {
                        _createTodolistItemState.value = State.Success(Unit)
                    },
                    onFailure = { throwable ->
                        _createTodolistItemState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch(e: Exception){
                _createTodolistItemState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                projectId?.let { getProjectsById(projectId) }
            }
        }
    }

    fun createProjectExpenses(projectId: String?, name: String){
        viewModelScope.launch {
            viewModelScope.launch {
                try {

                    if (_createExpensesState.value is State.Loading) {
                        Log.w("CREATE PROJECT EXPENSES", "Already processing, skipping")
                        return@launch
                    }

                    if(projectId == null) {
                        _createExpensesState.value = State.Error("Project ID id null")
                        return@launch
                    }

                    val result = projectExpensesRepository.createProjectExpenses(projectId = projectId, name = name)
                    result.fold(
                        onSuccess = {
                            _createExpensesState.value = State.Success(Unit)
                        },
                        onFailure = { throwable ->
                            _createExpensesState.value = State.Error(throwable.message ?: "Unknown Error")
                        }
                    )
                } catch (e: Exception) {
                    _createExpensesState.value = State.Error(e.message ?: "Unknown Error")
                } finally {
                    if(projectId !== null){
                        getProjectsById(projectId)
                    }
                }
            }
        }
    }

    fun createProjectExpensesItem(projectId: String?, expensesId: String, categoryName: String, name: String, amount: String){
        viewModelScope.launch {
            try {
                if (_createExpenseItemState.value is State.Loading) {
                    Log.w("CREATE PROJECT EXPENSES", "Already processing, skipping")
                    return@launch
                }

                val result = projectExpensesItemRepository.createProjectExpensesItem(
                    projectExpensesId = expensesId,
                    name = name,
                    amount = amount.toLong(),
                    categoryName = categoryName
                )
                result.fold(
                    onSuccess = {
                        _createExpenseItemState.value = State.Success(Unit)
                    },
                    onFailure = { throwable ->
                        _createExpenseItemState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch(e: Exception){
                _createExpenseItemState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                projectId?.let { getProjectsById(projectId) }
            }
        }
    }

    fun updateProjectTodolistById(id: String, projectId: String?, name: String){
        viewModelScope.launch {
            try {

                if (_createTodolistState.value is State.Loading) {
                    Log.w("CREATE PROJECT TODOLIST", "Already processing, skipping")
                    return@launch
                }

                if(projectId == null) {
                    _createTodolistState.value = State.Error("Project ID id null")
                    return@launch
                }

                val result = projectTodolistRepository.updateProjectTodolist(id = id, projectId = projectId, name = name)
                result.fold(
                    onSuccess = {
                        _createTodolistState.value = State.Success(Unit)
                    },
                    onFailure = { throwable ->
                        _createTodolistState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch (e: Exception) {
                _createTodolistState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                if(projectId !== null){
                    getProjectsById(projectId)
                }
            }
        }
    }

    fun updateTodolistItemById(){
        viewModelScope.launch {

        }
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