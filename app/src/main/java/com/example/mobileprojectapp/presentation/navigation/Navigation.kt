package com.example.mobileprojectapp.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileprojectapp.presentation.features.auth.LoginView
import com.example.mobileprojectapp.presentation.features.auth.RegisterView

@Composable
fun Navigation(context: Context) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "loginView", route = "root_graph") {

        composable(route = "loginView") { LoginView(navController) }
        composable(route = "registerView") { RegisterView(navController) }

    }
}