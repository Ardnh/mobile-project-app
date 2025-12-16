package com.example.mobileprojectapp.presentation.components.dialog

import android.os.Build
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import com.example.mobileprojectapp.presentation.components.bottomsheet.BaseBottomSheet
import com.example.mobileprojectapp.presentation.components.form.CustomInputSelectDate
import com.example.mobileprojectapp.presentation.components.form.CustomSelectInput
import com.example.mobileprojectapp.presentation.components.form.CustomTextField
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProjectDialog(
    onDismiss: () -> Unit,
    onCreateProject: (
        name: String,
        budget: Long,
        startDate: String,
        endDate: String,
        category: String
    ) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    var showBottomSheet by remember { mutableStateOf(false) }
    var showDatePickerStartDate by remember { mutableStateOf(false) }
    var showDatePickerEndDate by remember { mutableStateOf(false) }

    fun createProject(){
        onCreateProject(name, 100000, startDate, endDate, category)
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

                Text("Create new project")

                CustomTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Project name",
                )

                CustomTextField(
                    value = budget,
                    onValueChange = { budget = it },
                    placeholder = "Budget",
                    isNumericOnly = true
                )

                CustomInputSelectDate(
                    selectedDate = startDate,
                    title = "Start date",
                    showDatePicker = showDatePickerStartDate,
                    onDismissRequest = { showDatePickerStartDate = false },
                    onShowDatePicker = { showDatePickerStartDate = true },
                    onSelectDate = { value ->
                        startDate = value
                        showDatePickerStartDate = false
                    }
                )

                CustomInputSelectDate(
                    selectedDate = endDate,
                    title = "End date",
                    showDatePicker = showDatePickerEndDate,
                    onDismissRequest = { showDatePickerEndDate =  false },
                    onShowDatePicker = { showDatePickerEndDate = true },
                    onSelectDate = { value ->
                        endDate = value
                        showDatePickerEndDate = false
                    }
                )

                BaseBottomSheet(
                    title = "Project Category",
                    selectedCategory = category,
                    showBottomSheet = showBottomSheet,
                    onClickTrigger = { showBottomSheet = true },
                    onDismiss = { showBottomSheet = false },
                    onValueSelected = { it ->
                        category = it
                        showBottomSheet = false
                    }
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
                        onClick = { createProject() },
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
