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

    private val _deleteCategoryTodolistState = MutableStateFlow<State<Unit>>(State.Idle)
    val deleteCategoryTodolistState = _deleteCategoryTodolistState.asStateFlow()

    private val _deleteCategoryExpensesState = MutableStateFlow<State<Unit>>(State.Idle)
    val deleteCategoryExpensesState = _deleteCategoryExpensesState.asStateFlow()



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
                    onSuccess = { it ->
                        _createTodolistState.value = State.Success(Unit)

                        val currentDetail = _projectDetail.value
                        if(currentDetail is State.Success){
                            val data = currentDetail.data
                            val updateDetailProject = data.copy(
                                projectTodolists = currentDetail.data.projectTodolists + it
                            )
                            _projectDetail.value = State.Success(updateDetailProject)
                        }
                    },
                    onFailure = { throwable ->
                        _createTodolistState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch (e: Exception) {
                _createTodolistState.value = State.Error(e.message ?: "Unknown Error")
            }
        }
    }

    /**
     * Mengupdate kategori todolist yang sudah ada
     *
     * Fungsi ini melakukan operasi update asynchronous pada kategori project todolist.
     * Includes duplicate request prevention, comprehensive logging, error handling,
     * dan automatic data refresh setelah update selesai.
     *
     * @param item Object [UpdateCategoryTodolist] yang berisi:
     *   - id: ID unik dari todolist yang akan diupdate
     *   - projectId: ID project parent untuk refresh data
     *   - name: Nama kategori baru
     *
     * **Behavior:**
     * - Mencegah duplicate request dengan mengecek state loading
     * - Update [_createTodolistState] untuk reflect status operasi
     * - Log setiap step untuk debugging
     * - Automatically refresh project data setelah update
     *
     * **State Flow:**
     * ```
     * [Check Loading] -> [Update API] -> [Success/Error] -> [Refresh Data]
     * ```
     *
     * **Example Usage:**
     * ```kotlin
     * val updateItem = UpdateCategoryTodolist(
     *     id = "todo-123",
     *     projectId = "proj-456",
     *     name = "Updated Work Tasks"
     * )
     * viewModel.updateCategoryTodolistById(updateItem)
     * ```
     *
     * @see UpdateCategoryTodolist
     * @see State
     * @see getProjectsById
     */
    fun updateCategoryTodolistById(item: UpdateCategoryTodolist) {
        viewModelScope.launch {
            try {
                Log.d("UPDATE_CATEGORY_TODOLIST", "Starting updateCategoryTodolistById")
                Log.d("UPDATE_CATEGORY_TODOLIST", "Id: ${item.id}")
                Log.d("UPDATE_CATEGORY_TODOLIST", "ProjectId: ${item.projectId}")
                Log.d("UPDATE_CATEGORY_TODOLIST", "Name: ${item.name}")

                // Prevent duplicate/concurrent update requests
                if (_createTodolistState.value is State.Loading) {
                    Log.w("UPDATE_CATEGORY_TODOLIST", "Already processing, skipping duplicate request")
                    return@launch
                }

                Log.d("UPDATE_CATEGORY_TODOLIST", "Setting state to Loading")
                _createTodolistState.value = State.Loading

                Log.d("UPDATE_CATEGORY_TODOLIST", "Calling repository.updateProjectTodolist")
                val result = projectTodolistRepository.updateProjectTodolist(
                    id = item.id,
                    projectId = item.projectId,
                    name = item.name
                )

                result.fold(
                    onSuccess = {
                        Log.d("UPDATE_CATEGORY_TODOLIST", "Successfully updated category todolist")
                        Log.d("UPDATE_CATEGORY_TODOLIST", "Response: $it")
                        _createTodolistState.value = State.Success(Unit)
                    },
                    onFailure = { throwable ->
                        Log.e("UPDATE_CATEGORY_TODOLIST", "Failed to update category todolist")
                        Log.e("UPDATE_CATEGORY_TODOLIST", "Error: ${throwable.message}", throwable)
                        _createTodolistState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch (e: Exception) {
                Log.e("UPDATE_CATEGORY_TODOLIST", "Exception occurred: ${e.message}", e)
                _createTodolistState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                Log.d("UPDATE_CATEGORY_TODOLIST", "Refreshing project data")
                getProjectsById(item.projectId)
                Log.d("UPDATE_CATEGORY_TODOLIST", "Project refresh completed")
            }
        }
    }

    fun createProjectTodolistItem(
        projectId: String?,
        todolistId: String,
        categoryName: String,
        name: String
    ) {
        viewModelScope.launch {
            try {
                if (_createTodolistItemState.value is State.Loading) {
                    Log.w("CREATE PROJECT TODOLIST ITEM", "Already processing, skipping")
                    return@launch
                }

                _createTodolistItemState.value = State.Loading

                val result = projectTodolistItemRepository.createProjectTodolistItem(
                    todolistId = todolistId,
                    name = name,
                    categoryName = categoryName
                )

                result.fold(
                    onSuccess = { newTodolistItem ->
                        _createTodolistItemState.value = State.Success(Unit)

                        // Update existing project detail
                        val currentProjectDetail = _projectDetail.value
                        if (currentProjectDetail is State.Success) {
                            val updatedProjectDetail = currentProjectDetail.data.copy(
                                projectTodolists = currentProjectDetail.data.projectTodolists.map { todolist ->
                                    if (todolist.id == newTodolistItem.projectTodolistId) {
                                        todolist.copy(
                                            todolistItems = todolist.todolistItems + newTodolistItem
                                        )
                                    } else {
                                        todolist
                                    }
                                }
                            )

                            updatedProjectDetail.let { it ->
                                _projectDetail.value = State.Success(updatedProjectDetail)
                            }
                        }
                    },
                    onFailure = { throwable ->
                        _createTodolistItemState.value = State.Error(
                            throwable.message ?: "Unknown Error"
                        )
                    }
                )
            } catch (e: Exception) {
                _createTodolistItemState.value = State.Error(e.message ?: "Unknown Error")
            }
            // Removed finally block - no need to refetch
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

    /**
     * Membuat kategori expenses baru untuk project
     *
     * @param projectId ID project yang akan ditambahkan expenses-nya
     * @param name Nama kategori expenses
     */
    fun createProjectExpenses(projectId: String?, name: String){
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    Log.d("CREATE_EXPENSES", "Starting createProjectExpenses")
                    Log.d("CREATE_EXPENSES", "ProjectId: $projectId, Name: $name")

                    if (_createExpensesState.value is State.Loading) {
                        Log.w("CREATE_EXPENSES", "Already processing, skipping duplicate request")
                        return@launch
                    }

                    if(projectId == null) {
                        Log.e("CREATE_EXPENSES", "Project ID is null")
                        _createExpensesState.value = State.Error("Project ID is null")
                        return@launch
                    }

                    Log.d("CREATE_EXPENSES", "Setting state to Loading")
                    _createExpensesState.value = State.Loading

                    Log.d("CREATE_EXPENSES", "Calling repository.createProjectExpenses")
                    val result = projectExpensesRepository.createProjectExpenses(
                        projectId = projectId,
                        name = name
                    )

                    result.fold(
                        onSuccess = {
                            Log.d("CREATE_EXPENSES", "Successfully created expenses category")
                            Log.d("CREATE_EXPENSES", "Response: $it")
                            _createExpensesState.value = State.Success(Unit)

                            val currentProjectDetail = _projectDetail.value
                            if( currentProjectDetail is State.Success ){
                                val projectDetail = currentProjectDetail.data
                                val updatedProjectDetail = projectDetail.copy(
                                    projectExpenses = projectDetail.projectExpenses + it
                                )

                                _projectDetail.value = State.Success(updatedProjectDetail)
                            }
                        },
                        onFailure = { throwable ->
                            Log.e("CREATE_EXPENSES", "Failed to create expenses category")
                            Log.e("CREATE_EXPENSES", "Error: ${throwable.message}", throwable)
                            _createExpensesState.value = State.Error(throwable.message ?: "Unknown Error")
                        }
                    )
                } catch (e: Exception) {
                    Log.e("CREATE_EXPENSES", "Exception occurred: ${e.message}", e)
                    _createExpensesState.value = State.Error(e.message ?: "Unknown Error")
                } finally {
                    Log.d("CREATE_EXPENSES", "Refreshing project data")
                    projectId?.let {
                        getProjectsById(projectId)
                        Log.d("CREATE_EXPENSES", "Project refresh completed")
                    }
                }
            }
        }
    }

    fun updateCategoryExpensesById(item: UpdateCategoryTodolist){
        viewModelScope.launch {
            try {
                Log.d("UPDATE_CATEGORY_TODOLIST", "Starting updateCategoryTodolistById")
                Log.d("UPDATE_CATEGORY_TODOLIST", "Id: ${item.id}")
                Log.d("UPDATE_CATEGORY_TODOLIST", "ProjectId: ${item.projectId}")
                Log.d("UPDATE_CATEGORY_TODOLIST", "Name: ${item.name}")

                // Prevent duplicate/concurrent update requests
                if (_createTodolistState.value is State.Loading) {
                    Log.w("UPDATE_CATEGORY_TODOLIST", "Already processing, skipping duplicate request")
                    return@launch
                }

                Log.d("UPDATE_CATEGORY_TODOLIST", "Setting state to Loading")
                _createTodolistState.value = State.Loading

                Log.d("UPDATE_CATEGORY_TODOLIST", "Calling repository.updateProjectTodolist")
                val result = projectExpensesRepository.updateProjectExpenses(
                    id = item.id,
                    projectId = item.projectId,
                    name = item.name
                )

                result.fold(
                    onSuccess = {
                        Log.d("UPDATE_CATEGORY_TODOLIST", "Successfully updated category todolist")
                        Log.d("UPDATE_CATEGORY_TODOLIST", "Response: $it")
                        _createTodolistState.value = State.Success(Unit)
                    },
                    onFailure = { throwable ->
                        Log.e("UPDATE_CATEGORY_TODOLIST", "Failed to update category todolist")
                        Log.e("UPDATE_CATEGORY_TODOLIST", "Error: ${throwable.message}", throwable)
                        _createTodolistState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch (e: Exception) {
                Log.e("UPDATE_CATEGORY_TODOLIST", "Exception occurred: ${e.message}", e)
                _createTodolistState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                Log.d("UPDATE_CATEGORY_TODOLIST", "Refreshing project data")
                getProjectsById(item.projectId)
                Log.d("UPDATE_CATEGORY_TODOLIST", "Project refresh completed")
            }
        }
    }

    /**
     * Membuat item baru dalam kategori expenses
     *
     * @param projectId ID project untuk refresh data setelah create
     * @param expensesId ID kategori expenses tempat item akan ditambahkan
     * @param categoryName Nama kategori expenses
     * @param name Nama item expenses
     * @param amount Jumlah biaya (dalam string, akan dikonversi ke Long)
     */
    fun createProjectExpensesItem(
        projectId: String?,
        expensesId: String,
        categoryName: String,
        name: String,
        amount: String
    ){
        viewModelScope.launch {
            try {
                Log.d("CREATE_EXPENSE_ITEM", "Starting createProjectExpensesItem")
                Log.d("CREATE_EXPENSE_ITEM", "ProjectId: $projectId")
                Log.d("CREATE_EXPENSE_ITEM", "ExpensesId: $expensesId")
                Log.d("CREATE_EXPENSE_ITEM", "CategoryName: $categoryName")
                Log.d("CREATE_EXPENSE_ITEM", "Name: $name")
                Log.d("CREATE_EXPENSE_ITEM", "Amount: $amount")

                if (_createExpenseItemState.value is State.Loading) {
                    Log.w("CREATE_EXPENSE_ITEM", "Already processing, skipping duplicate request")
                    return@launch
                }

                Log.d("CREATE_EXPENSE_ITEM", "Setting state to Loading")
                _createExpenseItemState.value = State.Loading

                Log.d("CREATE_EXPENSE_ITEM", "Converting amount to Long")
                val amountLong = amount.toLong()
                Log.d("CREATE_EXPENSE_ITEM", "Amount converted: $amountLong")

                Log.d("CREATE_EXPENSE_ITEM", "Calling repository.createProjectExpensesItem")
                val result = projectExpensesItemRepository.createProjectExpensesItem(
                    projectExpensesId = expensesId,
                    name = name,
                    amount = amountLong,
                    categoryName = categoryName
                )

                result.fold(
                    onSuccess = { it ->
                        Log.d("CREATE_EXPENSE_ITEM", "Successfully created expense item")
                        Log.d("CREATE_EXPENSE_ITEM", "Response: $it")
                        _createExpenseItemState.value = State.Success(Unit)

                        val currentProjectDetail = _projectDetail.value
                        if( currentProjectDetail is State.Success ){
                            val projectDetail = currentProjectDetail.data
                            val updatedProjectDetail = projectDetail.copy(
                                projectExpenses = projectDetail.projectExpenses.map { expenses ->
                                    if( expenses.id === it.projectExpensesId ){
                                        expenses.copy(
                                            expensesItem = expenses.expensesItem + it
                                        )
                                    } else {
                                        expenses
                                    }
                                }
                            )
                            _projectDetail.value = State.Success(updatedProjectDetail)
                        }

                    },
                    onFailure = { throwable ->
                        Log.e("CREATE_EXPENSE_ITEM", "Failed to create expense item")
                        Log.e("CREATE_EXPENSE_ITEM", "Error: ${throwable.message}", throwable)
                        _createExpenseItemState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch(e: NumberFormatException){
                Log.e("CREATE_EXPENSE_ITEM", "Invalid amount format: $amount", e)
                _createExpenseItemState.value = State.Error("Invalid amount format")
            } catch(e: Exception){
                Log.e("CREATE_EXPENSE_ITEM", "Exception occurred: ${e.message}", e)
                _createExpenseItemState.value = State.Error(e.message ?: "Unknown Error")
            }
        }
    }

    /**
     * Mengupdate item expenses yang sudah ada
     *
     * @param projectId ID project untuk refresh data setelah update
     * @param expensesId ID item expenses yang akan diupdate
     * @param categoryName Nama kategori expenses
     * @param name Nama item expenses baru
     * @param amount Jumlah biaya baru (dalam string, akan dikonversi ke Long)
     */
    fun updateProjectExpensesItem(
        projectId: String?,
        expensesId: String,
        projectExpenseId: String,
        categoryName: String,
        name: String,
        amount: String
    ){
        viewModelScope.launch {
            try {
                Log.d("UPDATE_EXPENSE_ITEM", "Starting updateProjectExpensesItem")
                Log.d("UPDATE_EXPENSE_ITEM", "ProjectId: $projectId")
                Log.d("UPDATE_EXPENSE_ITEM", "ExpensesId: $expensesId")
                Log.d("UPDATE_EXPENSE_ITEM", "CategoryName: $categoryName")
                Log.d("UPDATE_EXPENSE_ITEM", "Name: $name")
                Log.d("UPDATE_EXPENSE_ITEM", "Amount: $amount")

                if (_updateExpenseItemState.value is State.Loading) {
                    Log.w("UPDATE_EXPENSE_ITEM", "Already processing, skipping duplicate request")
                    return@launch
                }

                Log.d("UPDATE_EXPENSE_ITEM", "Setting state to Loading")
                _updateExpenseItemState.value = State.Loading

                Log.d("UPDATE_EXPENSE_ITEM", "Converting amount to Long")
                val amountLong = amount.toLong()
                Log.d("UPDATE_EXPENSE_ITEM", "Amount converted: $amountLong")

                Log.d("UPDATE_EXPENSE_ITEM", "Calling repository.updateProjectExpensesItem")
                val result = projectExpensesItemRepository.updateProjectExpensesItem(
                    id = expensesId,
                    projectExpeseId = projectExpenseId,
                    name = name,
                    amount = amountLong,
                    categoryName = categoryName
                )

                result.fold(
                    onSuccess = { it ->
                        Log.d("UPDATE_EXPENSE_ITEM", "Successfully updated expense item")
                        Log.d("UPDATE_EXPENSE_ITEM", "Response: $it")
                        _updateExpenseItemState.value = State.Success(Unit)

                        val currentProjectDetail = _projectDetail.value
                        if( currentProjectDetail is State.Success ){
                            val projectDetail = currentProjectDetail.data
                            val updatedProjectDetail = projectDetail.copy(
                                projectExpenses = projectDetail.projectExpenses.map { expenses ->
                                    if( expenses.id === it.projectExpensesId ){
                                        expenses.copy(
                                            expensesItem = expenses.expensesItem.map { expensesItem ->
                                                if( expensesItem.id === it.id ){
                                                    expensesItem.copy(
                                                        name = it.name,
                                                        amount = it.amount
                                                    )
                                                } else {
                                                    expensesItem
                                                }
                                            }
                                        )
                                    } else {
                                        expenses
                                    }
                                }
                            )

                            _projectDetail.value = State.Success(updatedProjectDetail)

                        }

                    },
                    onFailure = { throwable ->
                        Log.e("UPDATE_EXPENSE_ITEM", "Failed to update expense item")
                        Log.e("UPDATE_EXPENSE_ITEM", "Error: ${throwable.message}", throwable)
                        _updateExpenseItemState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )
            } catch(e: NumberFormatException){
                Log.e("UPDATE_EXPENSE_ITEM", "Invalid amount format: $amount", e)
                _updateExpenseItemState.value = State.Error("Invalid amount format")
            } catch(e: Exception){
                Log.e("UPDATE_EXPENSE_ITEM", "Exception occurred: ${e.message}", e)
                _updateExpenseItemState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                Log.d("UPDATE_EXPENSE_ITEM", "Refreshing project data")
                projectId?.let {
                    getProjectsById(projectId)
                    Log.d("UPDATE_EXPENSE_ITEM", "Project refresh completed")
                } ?: Log.w("UPDATE_EXPENSE_ITEM", "ProjectId is null, skipping refresh")
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

    fun deleteCategoryTodolistById(projectId: String?, categoryTodolistId: String){
        viewModelScope.launch {
            try {

                if (_deleteCategoryTodolistState.value is State.Loading) {
                    Log.w("CREATE PROJECT", "Already processing, skipping")
                    return@launch
                }

                _deleteCategoryTodolistState.value = State.Loading
                val result = projectTodolistRepository.deleteProjectTodolist(categoryTodolistId)
                result.fold(
                    onSuccess = { data ->

                    },
                    onFailure = { throwable ->
                        _deleteCategoryTodolistState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )

            } catch (e: Exception){
                _deleteCategoryTodolistState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                _deleteCategoryTodolistState.value = State.Idle
                projectId?.let {
                    getProjectsById(projectId)
                }
            }
        }
    }

    fun deleteCategoryExpensesById(projectId: String?, categoryExpensesId: String){
        viewModelScope.launch {
            try {

                if (_deleteCategoryExpensesState.value is State.Loading) {
                    Log.w("CREATE PROJECT", "Already processing, skipping")
                    return@launch
                }

                _deleteCategoryExpensesState.value = State.Loading
                val result = projectExpensesRepository.deleteProjectExpenses(categoryExpensesId)
                result.fold(
                    onSuccess = { data ->

                    },
                    onFailure = { throwable ->
                        _deleteCategoryExpensesState.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )

            } catch (e: Exception){
                _deleteCategoryExpensesState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                _deleteCategoryExpensesState.value = State.Idle
                projectId?.let {
                    getProjectsById(projectId)
                }
            }
        }
    }

    fun deleteTodolistItemById(projectId: String?, id: String) {
        viewModelScope.launch {
            try {
                if (_deleteTodolistItemState.value is State.Loading) {
                    Log.w("DELETE_TODOLIST", "Delete already in progress, skipping. id=$id")
                    return@launch
                }

                if(projectId == null){
                    Log.w("Delete PROJECT", "Project ID NULL")
                    return@launch
                }

                Log.d("DELETE_TODOLIST", "Start deleting todolist item. id=$id")
                _deleteTodolistItemState.value = State.Loading

                val result = projectTodolistItemRepository.deleteProjectTodolistItem(id)

                result.fold(
                    onSuccess = {
                        Log.d("DELETE_TODOLIST", "Successfully deleted todolist item. id=$id")
                        getProjectsById(projectId)
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
                _deleteTodolistItemState.value = State.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun deleteExpenseItemById(projectId: String?, id: String) {
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
                _deleteExpenseItemState.value = State.Error(e.message ?: "Unknown Error")
            } finally {
                projectId?.let { it ->
                    getProjectsById(it)
                }
            }
        }
    }

}