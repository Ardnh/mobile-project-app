package com.example.mobileprojectapp.presentation.features.projectdetail

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mobileprojectapp.presentation.components.accordion.Accordion

@Composable
fun TodolistContent(){

    LazyColumn {

    }

    Accordion(
        title = "Pertanyaan 1",
        content = {
            Text("Ini adalah jawaban dari pertanyaan pertama. Anda bisa menambahkan konten apapun di sini.")
        }
    )
}