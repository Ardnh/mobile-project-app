package com.example.mobileprojectapp.presentation.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mobileprojectapp.domain.model.ExpensesItem
import com.example.mobileprojectapp.presentation.components.form.CustomTextField

@Composable
fun UpdateExpensesItemDialog(
    title: String,
    item: ExpensesItem,
    loading: Boolean,
    onDismiss: () -> Unit,
    onSaveUpdatedItem: (item: ExpensesItem) -> Unit
){

    var updateItemState by remember { mutableStateOf<ExpensesItem?>(null) }

    LaunchedEffect(item) {
        updateItemState = item
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

            if (updateItemState == null) {
                // Tampilan loading ketika data belum tersedia
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            strokeWidth = 4.dp
                        )
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                    ){
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "Group",
                                fontSize = 12.sp,
                                color = Color.Black,
                            )
                            Text(
                                text = updateItemState?.categoryName ?: "",
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    HorizontalDivider()

                    CustomTextField(
                        value = updateItemState?.name ?: "",
                        onValueChange = { it ->
                            updateItemState = updateItemState?.copy(name = it)
                        },
                        placeholder = "Expense name",
                        isPassword = false,
                        singleLine = true,
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    CustomTextField(
                        value = updateItemState?.amount ?: "0",
                        onValueChange = { it ->
                            updateItemState = updateItemState?.copy(amount = it)
                        },
                        placeholder = "Amount",
                        isPassword = false,
                        isNumericOnly = true,
                        singleLine = true,
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { onDismiss() },
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text("Close")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(
                            onClick = {  },
                            enabled = true,
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                            modifier = Modifier
                                .width(70.dp)
                        ) {

                            if(loading){
                                Box(
                                    modifier = Modifier.size(25.dp)
                                ){
                                    CircularProgressIndicator(
                                        strokeWidth = 3.dp
                                    )
                                }
                            } else {
                                Text(
                                    text = "Yes",
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}