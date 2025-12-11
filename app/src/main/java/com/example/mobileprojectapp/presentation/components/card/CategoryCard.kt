package com.example.mobileprojectapp.presentation.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryCard(
    title: String,
    count: Int,
    isSelected: Boolean,
    onClickCategory: (categoryName: String) -> Unit
){
    Box(
        modifier = Modifier
            .padding(bottom = 7.dp)
            .height(25.dp)
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
            .background(
                if (isSelected) Color.DarkGray else MaterialTheme.colorScheme.secondary
            )
            .clickable {
                onClickCategory(title)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$title (${count})",
            fontSize = 12.sp,
            color = if (isSelected) Color.White else Color.DarkGray,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )
    }
}