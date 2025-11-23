package com.example.mobileprojectapp.presentation.components.snackbar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackbar(
    message: String,
    actionLabel: String? = null,
    onActionClick: () -> Unit = {},
    type: SnackbarType = SnackbarType.INFO
) {
    Snackbar(
        modifier = Modifier
            .padding(16.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                clip = false
            )
            .clip(RoundedCornerShape(12.dp)),
        containerColor = MaterialTheme.colorScheme.surfaceBright,
        contentColor = type.contentColor,
        shape = RoundedCornerShape(12.dp),
        action = {
            actionLabel?.let {
                TextButton(onClick = onActionClick) {
                    Text(
                        text = it,
                        color = type.actionColor
                    )
                }
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = type.icon,
                contentDescription = null,
                tint = type.iconColor
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

enum class SnackbarType(
    val icon: ImageVector,
    val backgroundColor: Color,
    val contentColor: Color,
    val iconColor: Color,
    val borderColor: Color,
    val actionColor: Color
) {
    SUCCESS(
        icon = Icons.Default.CheckCircle,
        backgroundColor = Color(0xFF4CAF50).copy(alpha = 0.1f),
        contentColor = Color(0xFF1B5E20),
        iconColor = Color(0xFF4CAF50),
        borderColor = Color(0xFF4CAF50),
        actionColor = Color(0xFF1B5E20)
    ),
    ERROR(
        icon = Icons.Default.Error,
        backgroundColor = Color(0xFFF44336).copy(alpha = 0.1f),
        contentColor = Color(0xFFB71C1C),
        iconColor = Color(0xFFF44336),
        borderColor = Color(0xFFF44336),
        actionColor = Color(0xFFB71C1C)
    ),
    WARNING(
        icon = Icons.Default.Warning,
        backgroundColor = Color(0xFFFF9800).copy(alpha = 0.1f),
        contentColor = Color(0xFFE65100),
        iconColor = Color(0xFFFF9800),
        borderColor = Color(0xFFFF9800),
        actionColor = Color(0xFFE65100)
    ),
    INFO(
        icon = Icons.Default.Info,
        backgroundColor = Color(0xFF2196F3).copy(alpha = 0.1f),
        contentColor = Color(0xFF0D47A1),
        iconColor = Color(0xFF2196F3),
        borderColor = Color(0xFF2196F3),
        actionColor = Color(0xFF0D47A1)
    )
}