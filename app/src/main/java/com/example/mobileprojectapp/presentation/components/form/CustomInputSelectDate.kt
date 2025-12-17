package com.example.mobileprojectapp.presentation.components.form

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileprojectapp.utils.toFormattedDate
import com.example.mobileprojectapp.utils.toIsoDateString
import com.example.mobileprojectapp.utils.toMillis

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomInputSelectDate(
    title: String,
    selectedDate: String,
    showDatePicker: Boolean,
    onDismissRequest: () -> Unit,
    onShowDatePicker: () -> Unit,
    onSelectDate:(value: String) -> Unit
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDate = null
    )

    var formattedDate: String by remember { mutableStateOf("") }

    LaunchedEffect(selectedDate) {
        if (selectedDate.isNotEmpty()) {
            formattedDate = selectedDate.toFormattedDate()
            datePickerState.selectedDateMillis = selectedDate.toMillis()
        } else {
            formattedDate = title
            datePickerState.selectedDateMillis = null
        }
    }

    fun onSaveSelectedDate(){
        val timeMillis = datePickerState.selectedDateMillis
        if(timeMillis == null) {
            onSelectDate("")
            return
        }

        val dateStr = timeMillis.toIsoDateString()
        onSelectDate(dateStr)
        onDismissRequest()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {
                onShowDatePicker()
            }
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 28.dp, end = 15.dp)
        ) {
            Text(formattedDate.ifEmpty { title })
            Icon(
                imageVector = Icons.Rounded.CalendarMonth,
                contentDescription = "Calendar month icon",
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { onDismissRequest() },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            confirmButton = {
                TextButton(
                    onClick = {
                        onSaveSelectedDate()
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                colors =  DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                        )
                    }
                }
            )
        }
    }
}

