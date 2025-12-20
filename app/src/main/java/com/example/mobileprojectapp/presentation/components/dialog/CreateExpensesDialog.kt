package com.example.mobileprojectapp.presentation.components.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpensesDialog(
    title: String = "Unknown title",
    onDismiss: () -> Unit,
    onAddNewExpenses: (name: String) -> Unit,
) {

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

                Box(){
                    Text(
                        text = "Hello $title, this is todolist content"
                    )
                }

//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    TextButton(
//                        onClick = { onDismiss() },
//                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
//                    ) {
//                        Text("Close")
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    TextButton(
//                        onClick = { onDeleteProject() },
//                        enabled = loading !is State.Loading,
//                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
//                        modifier = Modifier
//                            .width(70.dp)
//                    ) {
//
//                        if(loading is State.Loading){
//                            Box(
//                                modifier = Modifier.size(25.dp)
//                            ){
//                                CircularProgressIndicator(
//                                    strokeWidth = 3.dp
//                                )
//                            }
//                        } else {
//                            Text(
//                                text = "Yes",
//                                color = Color.Black
//                            )
//                        }
//                    }
//                }
            }
        }
    }
}