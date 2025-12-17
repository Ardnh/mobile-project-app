package com.example.mobileprojectapp.presentation.components.dialog

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.mobileprojectapp.presentation.components.bottomsheet.BaseBottomSheet
import com.example.mobileprojectapp.presentation.components.form.CustomInputSelectDate
import com.example.mobileprojectapp.presentation.components.form.CustomSelectInput
import com.example.mobileprojectapp.presentation.components.form.CustomTextField
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProjectDialog(
    projectName: String = "",
    projectBudget: String = "",
    projectStartDate: String = "",
    projectEndDate: String = "",
    projectCategory: String = "",
    onDismiss: () -> Unit,
    onUpdateProject: (name: String, budget: String, startDate: String, endDate: String, category: String) -> Unit
) {

    var name by remember { mutableStateOf(projectName) }
    var budget by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(projectStartDate) }
    var endDate by remember { mutableStateOf(projectEndDate) }
    var category by remember { mutableStateOf(projectCategory) }

    var showBottomSheet by remember { mutableStateOf(false) }
    var showDatePickerStartDate by remember { mutableStateOf(false) }
    var showDatePickerEndDate by remember { mutableStateOf(false) }

    fun onSaveUpdateProject(){
        onUpdateProject( name, budget, startDate, endDate, category )
    }

    LaunchedEffect(projectBudget) {
        budget = projectBudget.replace(".", "")
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {

        Card(
            shape = RoundedCornerShape(25.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Update project",
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(5.dp))
                CustomTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Project name",
                )

                CustomTextField(
                    value = budget,
                    onValueChange = { budget = it },
                    placeholder = "Budget",
                )

                CustomInputSelectDate(
                    selectedDate = startDate,
                    title = "Start date",
                    showDatePicker = showDatePickerStartDate,
                    onDismissRequest = { showDatePickerStartDate = false },
                    onShowDatePicker = { showDatePickerStartDate = true },
                    onSelectDate = { value -> startDate = value }
                )

                CustomInputSelectDate(
                    selectedDate = endDate,
                    title = "End date",
                    showDatePicker = showDatePickerEndDate,
                    onDismissRequest = { showDatePickerEndDate =  false },
                    onShowDatePicker = { showDatePickerEndDate = true },
                    onSelectDate = { value -> endDate = value }
                )

                BaseBottomSheet(
                    title = "Project Category",
                    selectedCategory = category,
                    showBottomSheet = showBottomSheet,
                    onClickTrigger = { showBottomSheet = true },
                    onDismiss = { showBottomSheet = false },
                    onValueSelected = { it -> category = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text("Close")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = { onSaveUpdateProject() },
                        modifier = Modifier
                            .width(70.dp)
                    ) {
                        Text(
                            text = "Save",
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
