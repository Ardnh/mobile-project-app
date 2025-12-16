package com.example.mobileprojectapp.presentation.components.bottomsheet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileprojectapp.presentation.components.form.CustomTextField
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(
    showBottomSheet: Boolean,
    title: String,
    selectedCategory: String,
    onClickTrigger: () -> Unit,
    onDismiss: () -> Unit,
    onValueSelected: (value: String) -> Unit
){

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var selectedCategoryState by remember { mutableStateOf("") }
    var newCategoryState by remember { mutableStateOf("") }

    val items = listOf(
        "Kotlin", "Jetpack Compose", "Android", "Material Design",
        "UI/UX", "Mobile Development", "Clean Architecture", "MVVM",
        "UI/UX", "Mobile Development", "Clean Architecture", "MVVM",
    )

    fun onSaveCategory(){
        if(newCategoryState.isNotBlank()){
            onValueSelected(newCategoryState)
        } else {
            onValueSelected(selectedCategoryState)
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
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {

                LazyColumn(
                    contentPadding = PaddingValues(bottom = 20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        Text(
                            text = title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                        )
                    }

                    item {
                        CustomTextField(
                            value = newCategoryState,
                            onValueChange = { it -> newCategoryState = it },
                            placeholder = "New category",
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                        )
                    }

                    item{

                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items.forEach { item ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(30.dp))
                                        .background(if ( selectedCategoryState == item ) Color.LightGray else MaterialTheme.colorScheme.secondary)
                                        .clickable{
                                            newCategoryState = ""
                                            selectedCategoryState = item
                                        }
                                ){
                                    Text(
                                        text = item,
                                        modifier = Modifier
                                            .padding(horizontal = 20.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = { onSaveCategory() },
                    enabled = selectedCategoryState.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 0.dp)

                ) {
                    Text("Save")
                }
            }
        }
    }
}