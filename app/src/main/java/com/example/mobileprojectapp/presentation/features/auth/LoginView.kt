package com.example.mobileprojectapp.presentation.features.auth

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun LoginView(navigation: NavHostController) {

    Scaffold { innerPadding ->
        Text("this is login view", modifier = Modifier.padding(innerPadding))
    }

}