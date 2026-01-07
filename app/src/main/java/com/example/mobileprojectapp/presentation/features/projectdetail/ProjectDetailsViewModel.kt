package com.example.mobileprojectapp.presentation.features.projectdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.model.ProjectById
import com.example.mobileprojectapp.domain.model.ProjectCategory
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.domain.model.TodolistItem
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
    private val _updateTodolistItemState = MutableStateFlow<State<Unit>>(State.Idle)
    val updateTodolistItemState = _updateTodolistItemState.asStateFlow()
    private val _updateExpenseItemState = MutableStateFlow<State<Unit>>(State.Idle)
    val updateExpenseItemState = _updateExpenseItemState.asStateFlow()

    private val _deleteTodolistItemState = MutableStateFlow<State<Unit>>(State.Idle)
    val deleteTodolistItemState = _deleteTodolistItemState.asStateFlow()
    private val _deleteExpenseItemState = MutableStateFlow<State<Unit>>(State.Idle)
    val deleteExpenseItemState = _deleteExpenseItemState.asStateFlow()



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
                id?.let { getProjectsById(id) }
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
                projectId?.let { getProjectsById(projectId) }
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
                    projectId?.let { getProjectsById(projectId) }
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

    fun updateProjectExpensesItem(projectId: String?, expensesId: String, categoryName: String, name: String, amount: String){
        viewModelScope.launch {
            try {
                if (_updateExpenseItemState.value is State.Loading) {
                    Log.w("CREATE PROJECT EXPENSES", "Already processing, skipping")
                    return@launch
                }

                val result = projectExpensesItemRepository.updateProjectExpensesItem(
                    id = expensesId,
                    name = name,
                    amount = amount.toLong(),
                    categoryName = categoryName
                )
                result.fold(
                    onSuccess = {
                        _updateExpenseItemState.value = State.Success(Unit)
                    },
                    onFailure = { throwable ->
                        _updateExpenseItemState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch(e: Exception){
                _updateExpenseItemState.value = State.Error(e.message ?: "Unknown Error")
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
                projectId?.let { getProjectsById(projectId) }
            }
        }
    }

    fun updateTodolistItemById(projectId: String?, item: TodolistItem) {
        viewModelScope.launch {
            try {
                Log.d("UPDATE_TODOLIST_ITEM", "Starting update for item: ${item.id}, projectId: $projectId")

                if (_updateTodolistItemState.value is State.Loading) {
                    Log.w("UPDATE_TODOLIST_ITEM", "Already processing, skipping duplicate request")
                    return@launch
                }

                if (projectId == null) {
                    Log.e("UPDATE_TODOLIST_ITEM", "Project ID is null, aborting update")
                    _updateTodolistItemState.value = State.Error("Project ID is null")
                    return@launch
                }

                Log.d("UPDATE_TODOLIST_ITEM", "Calling repository with params - id: ${item.id}, name: ${item.name}, isCompleted: ${item.isCompleted}")

                val result = projectTodolistItemRepository.updateProjectTodolistItem(
                    id = item.id,
                    projectTodolistId = item.projectTodolistId,
                    name = item.name,
                    categoryName = item.categoryName,
                    isCompleted = item.isCompleted
                )

                result.fold(
                    onSuccess = {
                        Log.i("UPDATE_TODOLIST_ITEM", "Successfully updated item: ${item.id}")
                        _updateTodolistItemState.value = State.Success(Unit)
                    },
                    onFailure = { throwable ->
                        Log.e("UPDATE_TODOLIST_ITEM", "Failed to update item: ${throwable.message}", throwable)
                        _updateTodolistItemState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch (e: Exception) {
                Log.e("UPDATE_TODOLIST_ITEM", "Exception caught during update: ${e.message}", e)
                _updateTodolistItemState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                projectId?.let {
                    Log.d("UPDATE_TODOLIST_ITEM", "Refreshing project data for projectId: $it")
                    getProjectsById(projectId)
                }
            }
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

    fun deleteTodolistItemById(id: String) {
        viewModelScope.launch {
            try {
                if (_deleteTodolistItemState.value is State.Loading) {
                    Log.w("DELETE_TODOLIST", "Delete already in progress, skipping. id=$id")
                    return@launch
                }

                Log.d("DELETE_TODOLIST", "Start deleting todolist item. id=$id")
                _deleteTodolistItemState.value = State.Loading

                val result = projectTodolistItemRepository.deleteProjectTodolistItem(id)

                result.fold(
                    onSuccess = {
                        Log.d("DELETE_TODOLIST", "Successfully deleted todolist item. id=$id")
                        _deleteTodolistItemState.value = State.Idle
                    },
                    onFailure = { throwable ->
                        Log.e(
                            "DELETE_TODOLIST",
                            "Failed to delete todolist item. id=$id",
                            throwable
                        )
                        _deleteTodolistItemState.value =
                            State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch (e: Exception) {
                Log.e("DELETE_TODOLIST", "Unexpected error. id=$id", e)
                _deleteTodolistItemState.value =
                    State.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun deleteExpenseItemById(id: String) {
        viewModelScope.launch {
            try {
                if (_deleteExpenseItemState.value is State.Loading) {
                    Log.w("DELETE_EXPENSE", "Delete already in progress, skipping. id=$id")
                    return@launch
                }

                Log.d("DELETE_EXPENSE", "Start deleting expense item. id=$id")
                _deleteExpenseItemState.value = State.Loading

                val result = projectExpensesItemRepository.deleteProjectExpensesItem(id)

                result.fold(
                    onSuccess = {
                        Log.d("DELETE_EXPENSE", "Successfully deleted expense item. id=$id")
                        _deleteExpenseItemState.value = State.Idle
                    },
                    onFailure = { throwable ->
                        Log.e(
                            "DELETE_EXPENSE",
                            "Failed to delete expense item. id=$id",
                            throwable
                        )
                        _deleteExpenseItemState.value =
                            State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch (e: Exception) {
                Log.e("DELETE_EXPENSE", "Unexpected error. id=$id", e)
                _deleteExpenseItemState.value =
                    State.Error(e.message ?: "Unknown Error")
            }
        }
    }

}