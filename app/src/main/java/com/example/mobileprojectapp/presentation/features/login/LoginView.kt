package com.example.mobileprojectapp.presentation.features.login

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mobileprojectapp.R
import com.example.mobileprojectapp.presentation.components.form.CustomTextField
import com.example.mobileprojectapp.presentation.components.snackbar.CustomSnackbar
import com.example.mobileprojectapp.presentation.navigation.NavigationEvent
import com.example.mobileprojectapp.presentation.theme.KaushanFontFamily
import com.example.mobileprojectapp.presentation.theme.PrimaryFontFamily
import com.example.mobileprojectapp.utils.State


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun LoginView(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {

    val formState by viewModel.loginForm.collectAsState()
    val loginState by viewModel.loginResult.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = "navigation") {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToHome -> {
                    navController.navigate("HomeView") {
                        popUpTo("LoginView") { inclusive = true }
                        launchSingleTop = true
                    }
                }
                is NavigationEvent.NavigateBack -> {
                    navController.navigateUp()
                }

                is NavigationEvent.NavigateToDetail -> {}
                NavigationEvent.NavigateToLogin -> {}
            }
        }
    }

    LaunchedEffect(key1 = loginState) {
        if (loginState is State.Error) {
            snackbarHostState.showSnackbar(
                message = (loginState as State.Error).message,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ){
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.Top)
                        .padding(top = 25.dp),
                    snackbar = { data ->
                        CustomSnackbar(
                            message = data.visuals.message,
                            actionLabel = data.visuals.actionLabel,
                            onActionClick = { data.performAction() },
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
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
                    "Login",
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

                // Username field
                CustomTextField(
                    value = formState.username,
                    onValueChange = { viewModel.onUsernameChange(it) },
                    placeholder = "Username",
                    errorMessage = formState.usernameError
                )

                // Password field
                CustomTextField(
                    value = formState.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = "Password",
                    errorMessage = formState.passwordError,
                    isPassword = true
                )

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
                        enabled = loginState !is State.Loading,
                        onClick = {
                            viewModel.login()
//                            navController.navigate("HomeView")
                        },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White,
                            disabledContainerColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        if (loginState is State.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Sign In")
                        }
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
                        text = "Don't have account ?",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Sign Up",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .clickable{
                                navController.navigate("registerView")
                            }
                    )
                }

            }

        }
    }

}