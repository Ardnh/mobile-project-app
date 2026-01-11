package com.example.mobileprojectapp.presentation.components.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mobileprojectapp.presentation.components.form.CustomTextField
import com.example.mobileprojectapp.presentation.features.projectdetail.UpdateCategoryTodolist

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCategoryExpensesDialog(
    title: String = "Update Category",
    item: UpdateCategoryTodolist,
    loading: Boolean,
    onDismiss: () -> Unit,
    onUpdateTodolist: (UpdateCategoryTodolist) -> Unit,
) {
    var updateItemState by remember(item) {
        mutableStateOf(
            UpdateCategoryTodolist(
                id = item.id,
                projectId = item.projectId,
                name = item.name
            )
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = !loading,
            dismissOnClickOutside = !loading,
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
                // ✅ Header dengan styling lebih baik
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.primary
                )

                // ✅ TextField tanpa null check
                CustomTextField(
                    value = updateItemState.name,
                    onValueChange = { newName -> // ✅ Named parameter lebih jelas
                        updateItemState = updateItemState.copy(name = newName)
                    },
                    placeholder = "Category name",
                    isPassword = false,
                    singleLine = true,
                    enabled = !loading, // ✅ Disable saat loading
                    modifier = Modifier.fillMaxWidth()
                )

                // ✅ Validation message
                if (updateItemState.name.isBlank()) {
                    Text(
                        text = "Category name cannot be empty",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                // ✅ Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Cancel button
                    TextButton(
                        onClick = onDismiss,
                        enabled = !loading, // ✅ Disable saat loading
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Update button
                    TextButton(
                        onClick = {
                            if (updateItemState.name.isNotBlank()) {
                                onUpdateTodolist(updateItemState) // ✅ Kirim data
                            }
                        },
                        enabled = !loading && updateItemState.name.isNotBlank(), // ✅ Validasi
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                        modifier = Modifier.width(90.dp)
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        } else {
                            Text(
                                text = "Update",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}