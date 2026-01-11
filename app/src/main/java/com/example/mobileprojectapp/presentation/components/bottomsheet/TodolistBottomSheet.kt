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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
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
import com.example.mobileprojectapp.presentation.components.button.ButtonContent
import com.example.mobileprojectapp.presentation.components.button.ButtonIcon
import com.example.mobileprojectapp.presentation.components.view.EmptyProjectsView
import com.example.mobileprojectapp.presentation.features.projectdetail.UpdateCategoryTodolist

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodolistBottomSheet(
    showBottomSheet: Boolean,
    categoryTodolistId: String,
    title: String,
    todoInfo: String,
    category: String,
    todoList: List<TodolistItem> = emptyList(),
    onClickTrigger: () -> Unit,
    onDismiss: () -> Unit,
    onUpdateCategoryTodolist: () -> Unit,
    onAddNewTodolistItem: () -> Unit,
    onUpdateTodolistItemStatus: (todo: TodolistItem) -> Unit,
    onUpdateTodolistItem: (todo: TodolistItem) -> Unit,
    onDeleteTodolistItem: (id: String, title: String) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    fun updateTodolistItemStatus(todo: TodolistItem, isComplete: Boolean){
        val newTodo = todo.copy(
            isCompleted = isComplete
        )
        onUpdateTodolistItemStatus(newTodo)
    }

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

                     Row(
                         verticalAlignment = Alignment.CenterVertically
                     ) {
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


                         Row {
                             ButtonIcon(
                                 content = ButtonContent.IconOnly(
                                     icon = Icons.Rounded.Delete,
                                 ),
                                 onClick = { }
                             )
                             Spacer(modifier = Modifier.width(5.dp))
                             ButtonIcon(
                                 content = ButtonContent.IconOnly(
                                     icon = Icons.Rounded.Edit,
                                 ),
                                 onClick = { onUpdateCategoryTodolist() }
                             )
                         }
                     }

                    Spacer(modifier = Modifier.height(15.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(15.dp))
                }

                if(todoList.isEmpty()){
                    item{
                        EmptyProjectsView(
                            title = "Todolist item is empty",
                            description = "Start create new todolist item",
                            buttonLabel = "New todo item",
                            onClickBtn = { onAddNewTodolistItem() }
                        )
                    }
                } else {
                    item {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp)
                        ) {
                            ButtonIcon(
                                content = ButtonContent.IconAndText(
                                    icon = Icons.Rounded.Add,
                                    text = "Todo"
                                ),
                                onClick = { onAddNewTodolistItem() }
                            )
                        }
                    }

                    items(todoList) { it ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = it.isCompleted,
                                onCheckedChange = { value ->
                                    updateTodolistItemStatus(it, value)
                                }
                            )
                            Text(
                                text = it.name,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 5.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable{
                                        onUpdateTodolistItem(it)
                                    }
                            ){
                                Icon(
                                    imageVector = Icons.Rounded.Edit,
                                    contentDescription = "Edit expense item",
                                    modifier = Modifier
                                        .padding(5.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable{
                                        onDeleteTodolistItem(it.id, it.name)
                                    }
                            ){
                                Icon(
                                    imageVector = Icons.Rounded.Delete,
                                    contentDescription = "Delete expense item",
                                    modifier = Modifier
                                        .padding(5.dp)
                                )
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}