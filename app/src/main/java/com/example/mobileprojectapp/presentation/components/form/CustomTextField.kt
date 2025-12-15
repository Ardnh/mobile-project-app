package com.example.mobileprojectapp.presentation.components.form

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    onTrailingIconClick: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            enabled = enabled,
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            interactionSource = interactionSource,
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = if (isPassword && !passwordVisible)
                        PasswordVisualTransformation()
                    else
                        VisualTransformation.None,
                    interactionSource = interactionSource,
                    placeholder = { Text(placeholder) },
                    trailingIcon = when {
                        isPassword -> {
                            {
                                IconButton(
                                    onClick = { passwordVisible = !passwordVisible }
                                ) {
                                    Icon(
                                        imageVector = if (passwordVisible)
                                            Icons.Rounded.Visibility
                                        else
                                            Icons.Rounded.VisibilityOff,
                                        contentDescription = if (passwordVisible)
                                            "Hide password"
                                        else
                                            "Show password",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                        trailingIcon != null -> {
                            {
                                IconButton(
                                    modifier = Modifier
                                        .padding(vertical = 3.dp),
                                    onClick = {
                                        onTrailingIconClick?.invoke()
                                    }
                                ) {
                                    trailingIcon()
                                }
                            }
                        }
                        else -> null
                    },
                    shape = RoundedCornerShape(50.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = Color(0xFFF5F5F5),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 28.dp, vertical = 10.dp
                    ),
                    isError = errorMessage != null
                )
            }
        )

        // Error message
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 30.dp, top = 2.dp, bottom = 6.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}