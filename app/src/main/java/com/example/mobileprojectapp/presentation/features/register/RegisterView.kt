package com.example.mobileprojectapp.presentation.features.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mobileprojectapp.R
import com.example.mobileprojectapp.presentation.theme.KaushanFontFamily
import com.example.mobileprojectapp.presentation.theme.PrimaryFontFamily
import dagger.hilt.android.lifecycle.HiltViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun RegisterView(navigation: NavHostController, viewModel: RegisterViewModel = hiltViewModel()) {

    val state by viewModel.registerForm.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    val emailInteractionSource = remember { MutableInteractionSource() }
    val passwordInteractionSource = remember { MutableInteractionSource() }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(30.dp)
                    )
            ){
                Image(
                    painter = painterResource(id = R.drawable.auth_icon),
                    contentDescription = "Auth Icon",
                    modifier = Modifier
                        .padding(30.dp)
                        .size(80.dp)
                )
            }

            Text(
                "Q Project's",
                style = TextStyle(
                    fontFamily = KaushanFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 36.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Box(
                modifier = Modifier.height(40.dp)
            ){}

            Row(
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    "Sign Up",
                    style = TextStyle(
                        fontFamily = FontFamily(PrimaryFontFamily),
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 40.dp, vertical = 30.dp)
                    .fillMaxWidth(),
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicTextField(
                        value = state.username,
                        onValueChange = { viewModel.onUsernameChange(it) },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        interactionSource = emailInteractionSource,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .height(40.dp)
                            .fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            TextFieldDefaults.DecorationBox(
                                value = state.username,
                                innerTextField = innerTextField,
                                enabled = true,
                                singleLine = true,
                                visualTransformation = VisualTransformation.None,
                                interactionSource = emailInteractionSource,
                                placeholder = { Text("Username") },
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
                                // 👇 Tambahkan padding dalam untuk geser teks dari tepi kiri
                                contentPadding = PaddingValues(
                                    start = 30.dp,
                                    end = 8.dp,
                                    top = 0.dp,
                                    bottom = 0.dp
                                )
                            )
                        }
                    )

                    // Error message
                    if (state.usernameError != null) {
                        Text(
                            text = state.usernameError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 30.dp, top = 2.dp, bottom = 6.dp)
                        )
                    } else {
                        // Spacer untuk menjaga konsistensi tinggi layout
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicTextField(
                        value = state.email,
                        onValueChange = { viewModel.onEmailChange(it) },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        interactionSource = emailInteractionSource,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .height(40.dp)
                            .fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            TextFieldDefaults.DecorationBox(
                                value = state.email,
                                innerTextField = innerTextField,
                                enabled = true,
                                singleLine = true,
                                visualTransformation = VisualTransformation.None,
                                interactionSource = emailInteractionSource,
                                placeholder = { Text("Email") },
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
                                    start = 30.dp,
                                    end = 8.dp,
                                    top = 0.dp,
                                    bottom = 0.dp
                                ),
                                // Tambahkan border merah jika ada error
                                isError = state.emailError != null
                            )
                        }
                    )

                    // Error message
                    if (state.emailError != null) {
                        Text(
                            text = state.emailError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 30.dp, top = 2.dp, bottom = 6.dp)
                        )
                    } else {
                        // Spacer untuk menjaga konsistensi tinggi layout
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicTextField(
                        value = state.password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        interactionSource = passwordInteractionSource,
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                            .height(40.dp)
                            .fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            TextFieldDefaults.DecorationBox(
                                value = state.password,
                                innerTextField = innerTextField,
                                enabled = true,
                                singleLine = true,
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                interactionSource = passwordInteractionSource,
                                placeholder = { Text("Password") },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { passwordVisible = !passwordVisible }
                                    ) {
                                        Icon(
                                            imageVector = if (passwordVisible)
                                                Icons.Rounded.Visibility
                                            else
                                                Icons.Rounded.VisibilityOff,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                },
                                shape = RoundedCornerShape(50.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                // 👇 Ini inti dari solusi: padding internal teks
                                contentPadding = PaddingValues(
                                    start = 30.dp,
                                    end = 8.dp,
                                    top = 0.dp,
                                    bottom = 0.dp
                                )
                            )
                        }
                    )

                    // Error message
                    if (state.passwordError != null) {
                        Text(
                            text = state.passwordError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 30.dp, top = 2.dp, bottom = 6.dp)
                        )
                    } else {
                        // Spacer untuk menjaga konsistensi tinggi layout
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Text(
                        text = "Forgot Password ?",
                        fontSize = 14.sp
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { viewModel.register() },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White,
                            disabledContainerColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Sign Up")
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(30.dp)
                        )
                ) {
                    Text(
                        text = "Sign in with google",
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Already have account ?",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Sign In",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .clickable{
                                navigation.navigate("loginView")
                            }
                    )
                }

            }

        }
    }

}