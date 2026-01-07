package com.example.mobileprojectapp.presentation.components.dialog

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun UpdateTodolistItemDialog(
    onDismiss: () -> Unit
){
    Dialog(onDismissRequest = onDismiss) {
        Text("Update todolist item dialog")
    }
}