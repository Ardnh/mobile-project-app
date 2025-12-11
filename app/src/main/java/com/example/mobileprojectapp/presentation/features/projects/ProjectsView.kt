package com.example.mobileprojectapp.presentation.features.projects

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.presentation.components.card.CategoryCard
import com.example.mobileprojectapp.presentation.components.card.ProjectCard
import com.example.mobileprojectapp.presentation.components.form.CustomTextField
import com.example.mobileprojectapp.presentation.components.view.EmptyProjectsView
import com.example.mobileprojectapp.presentation.components.view.ErrorView
import com.example.mobileprojectapp.utils.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsView(navController: NavHostController, viewModel: ProjectsViewModel = hiltViewModel()){

    val projectListState by viewModel.projectList.collectAsState()
    val projectCategoryState by viewModel.projectCategory.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    val formState by viewModel.searchForm.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadInitialData()
    }

    LaunchedEffect(isRefreshing) {
        viewModel.loadInitialData()
        isRefreshing = false
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
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
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Kembali",
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        CustomTextField(
                            value = formState,
                            onValueChange = { viewModel.onSearchProjectChange(it) },
                            placeholder = "Search project",
                            isPassword = false,
                            singleLine = true,
                            enabled = true,
                            modifier = Modifier
                                .weight(1f),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = "search project"
                                )
                            },
                            onTrailingIconClick = {
                                viewModel.setProjectCategory(formState)
                            }
                        )

                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            contentDescription = "Filter projects",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(30.dp)
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
                // refresh logic
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
                                    CategoryCard(
                                        title = it.categoryName,
                                        count = it.total,
                                        isSelected = isSelected,
                                        onClickCategory = { it ->
                                            viewModel.setProjectCategory(it)
                                            selectedIndex = index
                                        }
                                    )
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

                    is State.Success<List<ProjectItem>> -> {
                        val projects = (projectListState as State.Success<List<ProjectItem>>).data
                        if(projects.isEmpty()){
                            item {
                                EmptyProjectsView()
                            }
                        } else {
                            items (projects) { project ->
                                ProjectCard(
                                    project = project,
                                    onClick = { projectId ->
                                        navController.navigate("ProjectDetailsView/${projectId}")
                                    }
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

                    State.Idle -> {}
                }
            }
        }
    }
}