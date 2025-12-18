package com.example.mobileprojectapp.presentation.components.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.mobileprojectapp.presentation.components.form.CustomTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    showBottomSheet: Boolean,
    title: String,
    categories: List<String> = emptyList(),
    selectedCategory: String,
    onClickTrigger: () -> Unit,
    onDismiss: () -> Unit,
    onValueSelected: (value: String) -> Unit
){

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var selectedCategoryState by remember { mutableStateOf("") }
    var newCategoryState by remember { mutableStateOf("") }

    fun onSaveCategory(){
        if(newCategoryState.isNotBlank()){
            onValueSelected(newCategoryState)
        } else {
            onValueSelected(selectedCategoryState)
        }
        onDismiss()
    }

    LaunchedEffect(newCategoryState) {
        if(newCategoryState.isNotBlank()){
            selectedCategoryState = newCategoryState
        }
    }

    LaunchedEffect(selectedCategory) {
        selectedCategoryState = selectedCategory
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
                .padding(start = 28.dp, end = 15.dp)
        ) {
            Text(selectedCategory.ifEmpty { "Select category" })
            Icon(
                imageVector = if (showBottomSheet) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                contentDescription = "Show on bottom sheet false",
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }

    if(showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
        ) {
            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                item {
                    Text(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }

                item {
                    CustomTextField(
                        value = newCategoryState,
                        onValueChange = { it -> newCategoryState = it },
                        placeholder = "New category",
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }

                item {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { item ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(
                                        if (selectedCategoryState == item) Color.LightGray
                                        else MaterialTheme.colorScheme.secondary
                                    )
                                    .clickable {
                                        newCategoryState = ""
                                        selectedCategoryState = item
                                    }
                            ) {
                                Text(
                                    text = item,
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { onSaveCategory() },
                        enabled = selectedCategoryState.isNotBlank(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}