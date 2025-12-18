package com.example.mobileprojectapp.presentation.features.projectdetail

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.More
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mobileprojectapp.domain.model.ProjectById
import com.example.mobileprojectapp.domain.model.ProjectCategory
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.presentation.components.accordion.Accordion
import com.example.mobileprojectapp.presentation.components.bottomsheet.TodolistBottomSheet
import com.example.mobileprojectapp.presentation.components.card.ProjectCard
import com.example.mobileprojectapp.presentation.components.dialog.AddExpensesDialog
import com.example.mobileprojectapp.presentation.components.dialog.AddTodolistDialog
import com.example.mobileprojectapp.presentation.components.dialog.DeleteProjectDialog
import com.example.mobileprojectapp.presentation.components.dialog.UpdateProjectDialog
import com.example.mobileprojectapp.presentation.components.dialog.UpdateTodolistDialog
import com.example.mobileprojectapp.presentation.components.dialog.UpdateTodolistItemDialog
import com.example.mobileprojectapp.presentation.components.view.EmptyProjectsView
import com.example.mobileprojectapp.presentation.components.view.ErrorView
import com.example.mobileprojectapp.presentation.features.home.HomeViewModel
import com.example.mobileprojectapp.presentation.theme.ColorPalette
import com.example.mobileprojectapp.utils.State
import com.google.android.material.tabs.TabItem

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailsView(navController: NavHostController, viewModel: ProjectDetailsViewModel = hiltViewModel(), projectId: String?){

    val projectDetailState by viewModel.projectDetail.collectAsState()
    val deleteProjectState by viewModel.deleteProjectState.collectAsState()
    val categoryListState by viewModel.projectCategory.collectAsState()

    var updateProjectState by remember { mutableStateOf<ProjectById?>(null) }

    var isRefreshing by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var todolistActiveIndex by remember { mutableIntStateOf(-1) }
    var isProjectMenuExpanded by remember { mutableStateOf(false) }
    var showUpdateProjectDialog by remember { mutableStateOf(false) }
    var showUpdateTodolistItemDialog by remember { mutableStateOf(false) }
    var showDeleteProjectDialog by remember { mutableStateOf(false) }
    var showAddTodolistOrExpensesDialog by remember { mutableStateOf(false) }
    val tabs = listOf("Todolist", "Expenses")

    LaunchedEffect(projectId, isRefreshing) {
        if (!projectId.isNullOrBlank()) {
            viewModel.getProjectsById(projectId)
            viewModel.getProjectCategory()
        }
        isRefreshing = false
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
                modifier = Modifier.size(56.dp),
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add project",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable{
                            showAddTodolistOrExpensesDialog = true
                        }
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
            },
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                when (projectDetailState) {
                    is State.Loading -> {
                        item {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            }
                        }
                    }

                    is State.Error -> {
                        val projectError = (projectDetailState as State.Error).message
                        item {
                            ErrorView(projectError , onRetry = {  })
                        }
                    }

                    is State.Success<ProjectById> -> {
                        val project = (projectDetailState as State.Success<ProjectById>).data
                        item {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xffFFC156)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp)
                                    .clip(RoundedCornerShape(25.dp))
                                    .height(200.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(20.dp)
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        Box() {
                                            Column {
                                                Row(
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 10.dp)
                                                ) {
                                                    Text(
                                                        text = "Project",
                                                        fontSize = 13.sp
                                                    )
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                    ) {
                                                        Box(
                                                            modifier = Modifier
                                                                .padding(end = 5.dp,)
                                                                .clip(RoundedCornerShape(20.dp))
                                                                .background(Color.White)
                                                        ){
                                                            Text(
                                                                text = project.categoryName,
                                                                fontSize = 11.sp,
                                                                lineHeight = 23.sp,
                                                                modifier = Modifier
                                                                    .padding(horizontal = 10.dp),
                                                            )
                                                        }
                                                        Box(
                                                            modifier = Modifier
                                                                .padding(end = 5.dp,)
                                                                .clip(RoundedCornerShape(20.dp))
                                                                .background(Color.White)
                                                        ){
                                                            Text(
                                                                text = "13 days left",
                                                                fontSize = 11.sp,
                                                                lineHeight = 23.sp,
                                                                modifier = Modifier
                                                                    .padding(horizontal = 10.dp),
                                                            )
                                                        }
                                                        Box(){
                                                            Icon(
                                                                imageVector = Icons.Rounded.MoreHoriz,
                                                                contentDescription = "More options",
                                                                modifier = Modifier
                                                                    .clickable{
                                                                        isProjectMenuExpanded = true
                                                                    }
                                                            )
                                                            DropdownMenu(
                                                                expanded = isProjectMenuExpanded,
                                                                onDismissRequest = { isProjectMenuExpanded = false },
                                                                containerColor = Color.White,
                                                                shape = RoundedCornerShape(20.dp)
                                                            ) {
                                                                DropdownMenuItem(
                                                                    text = { Text("Update") },
                                                                    onClick = {
                                                                        updateProjectState = project
                                                                        showUpdateProjectDialog =  true
                                                                    },
                                                                )
                                                                DropdownMenuItem(
                                                                    text = { Text("Delete") },
                                                                    onClick = {
                                                                        updateProjectState = project
                                                                        showDeleteProjectDialog =  true
                                                                    },
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                                Text(
                                                    text = project.name,
                                                    fontSize = 30.sp
                                                )
                                            }
                                        }
                                        Row(
                                            modifier = Modifier
                                                .height(50.dp)
                                                .fillMaxWidth()
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .weight(0.5f)
                                            ){
                                                Column {
                                                    Text(
                                                        text = "Budget",
                                                        fontSize = 15.sp
                                                    )
                                                    Text(
                                                        text = project.budget,
                                                        fontWeight = FontWeight.SemiBold
                                                    )
                                                }
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .weight(0.5f)
                                            ){
                                                Column {
                                                    Text(
                                                        text = "Used",
                                                        fontSize = 15.sp
                                                    )
                                                    Text(
                                                        text = "${project.budgetUsed}",
                                                        fontWeight = FontWeight.SemiBold
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            PrimaryTabRow(
                                containerColor = ColorPalette.Gray200,
                                selectedTabIndex = selectedTab,
                                indicator = {},
                                divider = {},
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .border(
                                        width = 1.dp,
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(30.dp)
                                    )
                            ) {
                                tabs.forEachIndexed { index, tab ->
                                    Tab(
                                        selected = selectedTab == index,
                                        onClick = { selectedTab = index },
                                        modifier = Modifier
                                            .indication(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = ripple(
                                                    bounded = true,
                                                    color = Color.Gray
                                                )
                                            )
                                            .padding(3.dp)
                                            .height(35.dp)
                                            .clip(RoundedCornerShape(30.dp))
                                            .background(
                                                if (selectedTab == index) ColorPalette.Gray300
                                                else Color.Transparent
                                            )
                                        ,
                                        text = {
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                Text(
                                                    text = if(index == 0) "$tab (${project.totalTodolistCompletedItem}/${project.totalTodolistItem})" else tab,
                                                    color = Color.DarkGray
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    State.Idle -> {}
                }

                when (projectDetailState) {
                    is State.Loading -> {
                        item {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            }
                        }
                    }

                    is State.Error -> {
                        val projectError = (projectDetailState as State.Error).message
                        item {
                            ErrorView(projectError , onRetry = {  })
                        }
                    }

                    is State.Success<ProjectById> -> {
                        val project = (projectDetailState as State.Success<ProjectById>).data
                        when (selectedTab) {
                            0 -> {

                                if(project.projectTodolists.isEmpty()){
                                    item {
                                        EmptyProjectsView(
                                            title = "Todolist is empty",
                                            description = "Start create new todolist",
                                            buttonLabel = "New todolist",
                                            onClickBtn = null
                                        )
                                    }
                                } else {
                                    itemsIndexed(project.projectTodolists) { parentIndex, todo ->

                                        TodolistBottomSheet(
                                            showBottomSheet = todolistActiveIndex == parentIndex,
                                            title = todo.name,
                                            todoInfo = "${todo.totalCompletedTodo}/${todo.totalTodo}",
                                            todoList = todo.todolistItems,
                                            onClickTrigger = { todolistActiveIndex = parentIndex },
                                            onDismiss = { todolistActiveIndex = -1 }
                                        )

                                    }
                                }
                            }
                            1 -> item {
                                if(project.projectExpenses.isEmpty()){
                                    EmptyProjectsView(
                                        title = "Expenses is empty",
                                        description = "Start create new expenses",
                                        buttonLabel = "New expenses",
                                        onClickBtn = null
                                    )
                                } else {
                                    ExpensesContent()
                                }
                            }
                        }
                    }

                    State.Idle -> {}
                }
            }
        }
    }

    if(showAddTodolistOrExpensesDialog && selectedTab == 0){
        AddTodolistDialog(
            title = "Add new Todolist",
            onDismiss = { showAddTodolistOrExpensesDialog = false },
            onAddNewTodolist = {  }
        )
    }

    if(showAddTodolistOrExpensesDialog && selectedTab == 1){
        AddExpensesDialog(
            title = "Add new Expenses",
            onDismiss = { showAddTodolistOrExpensesDialog = false },
            onAddNewExpenses = {  }
        )
    }

    if(showUpdateProjectDialog && updateProjectState !== null){

        val categoryList = if(categoryListState is State.Success) (categoryListState as State.Success<List<ProjectCategory>>).data.map { it.categoryName } else emptyList()
        UpdateProjectDialog(
            projectName = updateProjectState?.name ?: "",
            projectBudget = updateProjectState?.budget ?: "",
            projectStartDate = updateProjectState?.startDate ?: "",
            projectEndDate = updateProjectState?.endDate ?: "",
            projectCategory = updateProjectState?.categoryName ?: "",
            onDismiss = { showUpdateProjectDialog = false },
            categoryList = categoryList,
            onUpdateProject = { name, budget, startDate, endDate, category ->
                viewModel.updateProjectById(
                    id = updateProjectState?.id,
                    userId = updateProjectState?.userId,
                    name = name,
                    budget = budget,
                    startDate = startDate,
                    endDate = endDate,
                    category = category
                )
                showUpdateProjectDialog = false
                isProjectMenuExpanded = false
            }
        )
    }

    if(showUpdateTodolistItemDialog){
        UpdateTodolistItemDialog(
            onDismiss = { showUpdateTodolistItemDialog = false }
        )
    }

    if(showDeleteProjectDialog && updateProjectState != null){
        DeleteProjectDialog(
            loading = deleteProjectState,
            title = updateProjectState?.name ?: "",
            onDismiss = {
                showDeleteProjectDialog = false
                updateProjectState = null
            },
            onDeleteProject = {
                viewModel.deleteProjectById(updateProjectState?.id)
                showDeleteProjectDialog = false
                navController.navigateUp()
            }
        )
    }

}