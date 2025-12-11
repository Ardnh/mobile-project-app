package com.example.mobileprojectapp.presentation.features.projectdetail

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.More
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mobileprojectapp.domain.model.ProjectById
import com.example.mobileprojectapp.domain.model.ProjectItem
import com.example.mobileprojectapp.presentation.components.accordion.Accordion
import com.example.mobileprojectapp.presentation.components.card.ProjectCard
import com.example.mobileprojectapp.presentation.components.view.EmptyProjectsView
import com.example.mobileprojectapp.presentation.components.view.ErrorView
import com.example.mobileprojectapp.presentation.features.home.HomeViewModel
import com.example.mobileprojectapp.presentation.theme.ColorPalette
import com.example.mobileprojectapp.utils.State
import com.google.android.material.tabs.TabItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailsView(navController: NavHostController, viewModel: ProjectDetailsViewModel = hiltViewModel(), projectId: String?){

    val projectDetailState by viewModel.projectDetail.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Todolist (2/13)", "Expenses")

    LaunchedEffect(isRefreshing) {
        if (!projectId.isNullOrBlank()) {
            viewModel.getProjectsByUserId(projectId)
        }
        isRefreshing = false
    }

    LaunchedEffect(projectId) {
        if (!projectId.isNullOrBlank()) {
            viewModel.getProjectsByUserId(projectId)
        }
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
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 10.dp)
                                                ) {
                                                    Text(
                                                        text = "Project"
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
                                                                text = "13 days left",
                                                                fontSize = 11.sp,
                                                                lineHeight = 23.sp,
                                                                modifier = Modifier
                                                                    .padding(horizontal = 10.dp),
                                                            )
                                                        }
                                                        Icon(
                                                            imageVector = Icons.Rounded.MoreHoriz,
                                                            contentDescription = "More options",
                                                        )
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
                                                        text = "Rp 120.000.000",
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
                                                        text = "Rp 80.000.000",
                                                        fontWeight = FontWeight.SemiBold
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    State.Idle -> {}
                }

                item {
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
                                            text = tab,
                                            color = Color.DarkGray
                                        )
                                    }
                                }
                            )
                        }
                    }
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
                                        EmptyProjectsView()
                                    }
                                } else {
                                    items(project.projectTodolists) { todo ->
                                        Accordion(
                                            title = todo.name,
                                            content = {
                                                Text("Ini adalah jawaban dari pertanyaan pertama. Anda bisa menambahkan konten apapun di sini.")
                                            }
                                        )
                                    }
                                }

                            }
                            1 -> item {
                                if(project.projectExpenses.isEmpty()){
                                    EmptyProjectsView()
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
}