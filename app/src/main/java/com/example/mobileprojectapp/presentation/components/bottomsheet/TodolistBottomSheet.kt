package com.example.mobileprojectapp.presentation.components.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowRight
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileprojectapp.domain.model.TodolistItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodolistBottomSheet(
    showBottomSheet: Boolean,
    title: String,
    todoInfo: String,
    category: String,
    todoList: List<TodolistItem> = emptyList(),
    onClickTrigger: () -> Unit,
    onDismiss: () -> Unit,
    onUpdateTodo: (todo: TodolistItem, isComplete: Boolean) -> Unit,
    onAddNewTodolistItem: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable{
                onClickTrigger()
            }
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 15.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowRight,
                contentDescription = "Show on bottom sheet false",
                modifier = Modifier
                    .size(30.dp)
            )
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = todoInfo,
                modifier = Modifier
            )
        }
    }

    if(showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 20.dp)
            ) {
                item {
                    Text(
                        text = title,
                        modifier = Modifier
                            .weight(1f),
                        maxLines = 2,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(MaterialTheme.colorScheme.primary)
                                .clickable{ onAddNewTodolistItem() }
                        ){
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Add,
                                    contentDescription = "Add new todo",
                                    tint = Color.White
                                )

                                Text(
                                    text = "Todo",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                items(todoList) { it ->
                     Row(
                         verticalAlignment = Alignment.CenterVertically,
                         modifier = Modifier
                             .fillMaxWidth()
                     ) {
                         Text(
                             text = it.name,
                             maxLines = 2,
                             overflow = TextOverflow.Ellipsis,
                             modifier = Modifier
                                 .weight(1f)
                                 .padding(vertical = 5.dp)
                         )
                         Checkbox(
                             checked = it.isCompleted,
                             onCheckedChange = { value ->
                                 onUpdateTodo(it, value)
                             }
                         )
                     }
                     HorizontalDivider()
                 }
            }
        }
    }
}