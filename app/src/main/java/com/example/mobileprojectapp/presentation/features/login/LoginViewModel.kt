package com.example.mobileprojectapp.presentation.features.login

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.repository.AuthRepository
import com.example.mobileprojectapp.domain.repository.UserRepository
import com.example.mobileprojectapp.presentation.navigation.NavigationEvent
import com.example.mobileprojectapp.utils.LoginValidator
import com.example.mobileprojectapp.utils.SecureStorageManager
import com.example.mobileprojectapp.utils.State
import com.example.mobileprojectapp.utils.isExpired
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository, private val userRepository: UserRepository, private val storage: SecureStorageManager) : ViewModel() {

    // -----------------------------
    // UI State
    // -----------------------------
    private val _loginForm = MutableStateFlow(LoginState())
    val loginForm = _loginForm.asStateFlow()

    // SharedFlow untuk navigation events (one-time events)
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>(replay = 1)
    val navigationEvent = _navigationEvent.asSharedFlow()

    // -----------------------------
    // API Result State
    // -----------------------------
    private val _loginResult = MutableStateFlow<State<Unit>>(State.Idle)
    val loginResult: StateFlow<State<Unit>> = _loginResult.asStateFlow()

    // -----------------------------
    // UI Event Actions
    // -----------------------------
    fun onUsernameChange(value: String){
        _loginForm.value = _loginForm.value.copy(username = value)
    }
    fun onPasswordChange(value: String){
        _loginForm.value = _loginForm.value.copy(password = value)
    }

    // -----------------------------
    // API Actions
    // -----------------------------

    fun login(){
        viewModelScope.launch {

            _loginResult.value = State.Loading

            val state = _loginForm.value
            val validation = LoginValidator.validate(
                username = state.username,
                password = state.password
            )

            _loginForm.value = state.copy(
                usernameError = validation.usernameError,
                passwordError = validation.passwordError
            )

            if (!validation.isValid) {
                _loginResult.value = State.Idle
                return@launch
            }

            try {
                val result =  authRepository.login(state.username, state.password)
                result.fold(
                    onSuccess = { data ->
                        storage.saveAuthToken(data.token)
                        storage.saveAuthTokenExpireDate(data.expiredAt)
                        _loginResult.value = State.Success(Unit)

                        val profileResult = userRepository.getUserProfile()
                        profileResult.fold(
                            onSuccess = { userProfile ->

                                storage.saveUserId(userProfile.id)
                                storage.saveUsername(userProfile.username)

                                _loginResult.value = State.Success(Unit)
                                _navigationEvent.emit(NavigationEvent.NavigateToHome)
                            },
                            onFailure = { throwable ->
                                Log.d("Get profile", throwable.message ?: "Unknown error")
                                _loginResult.value = State.Error(
                                    "Login successful but failed to load profile: ${throwable.message}"
                                )
                                // Optional: Tetap navigate atau tidak
                                // _navigationEvent.emit(NavigationEvent.NavigateToHome)
                            }
                        )
                    },
                    onFailure = { throwable ->
                        _loginResult.value = State.Error(throwable.message ?: "Unknown Error")
                    }
                )

            } catch (e : Exception){
                _loginResult.value = State.Error(e.message ?: "Unkown Error")
            }
        }
    }

}