package com.example.mobileprojectapp.presentation.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileprojectapp.presentation.features.login.LoginView
import com.example.mobileprojectapp.presentation.features.register.RegisterView
import com.example.mobileprojectapp.presentation.features.home.HomeView
import com.example.mobileprojectapp.presentation.features.projectdetail.ProjectDetailsView
import com.example.mobileprojectapp.presentation.features.projects.ProjectsView

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Navigation(context: Context) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "loginView", route = "root_graph") {

        composable(route = "LoginView") { LoginView(navController) }
        composable(route = "RegisterView") { RegisterView(navController) }
        composable(route = "HomeView") { HomeView(navController) }
        composable(route = "ProjecsView") { ProjectsView(navController) }
        composable(route = "ProjectDetailsView") { ProjectDetailsView(navController) }

    }
}