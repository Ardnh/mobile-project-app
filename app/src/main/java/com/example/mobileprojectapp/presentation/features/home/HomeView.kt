package com.example.mobileprojectapp.presentation.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.domain.model.ProjectSummary
import com.example.mobileprojectapp.domain.model.UserProfile
import com.example.mobileprojectapp.presentation.components.card.ProjectCard
import com.example.mobileprojectapp.presentation.components.view.EmptyProjectsView
import com.example.mobileprojectapp.presentation.components.view.ErrorView
import com.example.mobileprojectapp.presentation.features.home.components.SummarySection
import com.example.mobileprojectapp.utils.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navigation: NavHostController, viewModel: HomeViewModel = hiltViewModel()){

    val projectListState by viewModel.projectList.collectAsState()
    val projectCategoryState by viewModel.projectCategory.collectAsState()
    val projectSummaryState by viewModel.projectSummary.collectAsState()
    val userProfileState by viewModel.userProfile.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    var isRefreshing by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadInitialData()
    }

    LaunchedEffect(isRefreshing) {
        viewModel.loadInitialData()
        isRefreshing = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                actions = {
                    Image(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Menu Icon",
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .size(30.dp)
                    )
                },
                modifier = Modifier
                    .statusBarsPadding()
                    .height(40.dp)
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                // refresh logic
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                item {
                    val data = when(userProfileState) {
                        is State.Success -> (userProfileState as State.Success<UserProfile>).data
                        else -> null
                    }

                    Text(
                        text = "Hello, ${ data?.username ?: "Unknown Name" }",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }

                item {
                    val data = when (projectSummaryState) {
                        is State.Success -> (projectSummaryState as State.Success<ProjectSummary>).data
                        else -> null
                    }

                    SummarySection(
                        totalProjects = data?.totalProjects ?: "0",
                        totalProjectsDone = data?.totalProjectsDone ?: "0",
                        budgetUsed = data?.totalBudgetUsed ?: "0"
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recent Projects",
                            fontWeight = FontWeight.Bold
                        )
                        TextButton(
                            onClick = {}
                        ) {
                            Text("See All projects")
                        }
                    }
                }

                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 0.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        when(projectCategoryState) {
                            is State.Error -> {}
                            is State.Idle -> {}
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

                            is State.Success -> {
                                val category = (projectCategoryState as State.Success).data
                                itemsIndexed(category) { index, it ->
                                    val isSelected = index == selectedIndex
                                    Box(
                                        modifier = Modifier
                                            .padding(bottom = 7.dp)
                                            .height(30.dp)
                                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                                            .border(
                                                width = 2.dp,
                                                color = if (isSelected) Color.Transparent else Color.LightGray,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                            .background(
                                                if (isSelected) Color.LightGray else Color.Transparent
                                            )
                                            .clickable {
                                                selectedIndex = index
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${it.categoryName} (${it.total})",
                                            modifier = Modifier
                                                .padding(horizontal = 15.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                when(projectListState) {
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
                        val projectError = (projectListState as State.Error).message
                        item {
                            ErrorView(projectError , onRetry = {  })
                        }
                    }

                    is State.Success<List<ProjectItem>> -> {
                        val projects = (projectListState as State.Success<List<ProjectItem>>).data
                        if(projects.isEmpty()){
                            item {
                                EmptyProjectsView()
                            }
                        } else {
                            items (projects) { project ->
                                ProjectCard(project = project)
                            }
                        }
                    }

                    State.Idle -> {}
                }

            }
        }
    }
}