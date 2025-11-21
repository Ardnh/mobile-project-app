package com.example.mobileprojectapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileprojectapp.presentation.navigation.Navigation
import com.example.mobileprojectapp.presentation.theme.MobileProjectAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        val appContext = applicationContext
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileProjectAppTheme {
                Navigation(appContext)
            }
        }
    }
}