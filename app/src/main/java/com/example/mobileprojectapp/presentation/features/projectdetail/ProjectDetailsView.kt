package com.example.mobileprojectapp.presentation.features.projectdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ProjectDetailsView(navigation: NavHostController){
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ){
            Text("This is project details view")
        }
    }
}