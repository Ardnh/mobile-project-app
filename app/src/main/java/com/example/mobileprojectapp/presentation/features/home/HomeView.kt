package com.example.mobileprojectapp.presentation.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun HomeView(navigation: NavHostController, viewModel: HomeViewModel = hiltViewModel()){

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {

    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ){
            Text("This is Home view")
        }
    }
}