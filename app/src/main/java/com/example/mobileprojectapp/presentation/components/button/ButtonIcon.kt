package com.example.mobileprojectapp.presentation.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ✅ REKOMENDASI: Gunakan sealed class untuk state yang jelas
sealed class ButtonContent {
    data class IconOnly(val icon: ImageVector) : ButtonContent()
    data class TextOnly(val text: String) : ButtonContent()
    data class IconAndText(val icon: ImageVector, val text: String) : ButtonContent()
}

@Composable
fun ButtonIcon(
    content: ButtonContent,
    radius: Dp = 20.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {

    val contentIsTextOnly = content is ButtonContent.TextOnly
    val contentIsIconAndText = content is ButtonContent.IconAndText
    val contentIsIconOnly = !contentIsTextOnly && !contentIsIconAndText

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(radius))
            .background(backgroundColor)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = when(content) {
                    is ButtonContent.IconOnly -> 10.dp
                    else -> 10.dp
                }
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (content) {
                is ButtonContent.IconOnly -> {
                    Icon(
                        imageVector = content.icon,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                is ButtonContent.TextOnly -> {
                    Text(
                        text = content.text,
                        color = Color.White
                    )
                }
                is ButtonContent.IconAndText -> {
                    Icon(
                        imageVector = content.icon,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(text = content.text, color = Color.White)
                }
            }
        }
    }
}