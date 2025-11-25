package com.example.mobileprojectapp.presentation.features.splash

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mobileprojectapp.presentation.navigation.NavigationEvent


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SplashView(navController: NavHostController, viewModel: SplashViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.checkExistingAuth()
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when(event) {
                NavigationEvent.NavigateToHome -> {
                    navController.navigate("HomeView") {
                        popUpTo("SplashView") { inclusive = true }
                    }
                }
                NavigationEvent.NavigateToLogin -> {
                    navController.navigate("LoginView") {
                        popUpTo("SplashView") { inclusive = true }
                    }
                }

                NavigationEvent.NavigateBack -> {}
                is NavigationEvent.NavigateToDetail -> {}
            }
        }
    }

    // tampilin logo / loading
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        CircularProgressIndicator()
    }
}
