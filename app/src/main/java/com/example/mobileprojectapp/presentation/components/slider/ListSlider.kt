package com.example.mobileprojectapp.presentation.components.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileprojectapp.utils.State

@Composable
fun ListSlider(){
//    LazyRow(
//        contentPadding = PaddingValues(horizontal = 0.dp),
//        horizontalArrangement = Arrangement.spacedBy(5.dp)
//    ) {
//
//        when(projectCategoryState) {
//            is State.Error -> {}
//            is State.Idle -> {}
//            is State.Loading -> {
//                item {
//                    Box(
//                        contentAlignment = Alignment.Center,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(100.dp)
//                    ) {
//                        CircularProgressIndicator(
//                            modifier = Modifier.size(20.dp),
//                            color = Color.White,
//                            strokeWidth = 2.dp
//                        )
//                    }
//                }
//            }
//
//            is State.Success -> {
//                val category = (projectCategoryState as State.Success).data
//                itemsIndexed(category) { index, it ->
//                    val isSelected = index == selectedIndex
//                    Box(
//                        modifier = Modifier
//                            .padding(bottom = 7.dp)
//                            .height(25.dp)
//                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
//                            .border(
//                                width = 1.dp,
//                                color = if (isSelected) Color.Transparent else Color.LightGray,
//                                shape = RoundedCornerShape(20.dp)
//                            )
//                            .background(
//                                if (isSelected) Color.LightGray else Color.Transparent
//                            )
//                            .clickable {
//                                selectedIndex = index
//                            },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "${it.categoryName} (${it.total})",
//                            fontSize = 12.sp,
//                            modifier = Modifier
//                                .padding(horizontal = 12.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
}