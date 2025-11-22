package com.example.mobileprojectapp.presentation.features.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.repository.AuthRepository
import com.example.mobileprojectapp.utils.RegisterValidator
import com.example.mobileprojectapp.utils.SecureStorageManager
import com.example.mobileprojectapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository, private val storage: SecureStorageManager) : ViewModel() {

    // -----------------------------
    // UI State
    // -----------------------------
    private val _registerForm = MutableStateFlow(RegisterState())
    val registerForm = _registerForm.asStateFlow()

    // -----------------------------
    // API Result State
    // -----------------------------
    private val _registerState = MutableStateFlow<State<Unit>>(State.Idle)
    val registerState: StateFlow<State<Unit>> = _registerState

    // -----------------------------
    // UI Event Actions
    // -----------------------------
    fun onEmailChange(value: String) {
        _registerForm.value = _registerForm.value.copy(email = value)
    }
    fun onUsernameChange(value: String) {
        _registerForm.value = _registerForm.value.copy(username = value)
    }
    fun onPasswordChange(value: String) {
        _registerForm.value = _registerForm.value.copy(password = value)
    }



    // -----------------------------
    // API Actions
    // -----------------------------
    fun register(){
        viewModelScope.launch {

            _registerState.value = State.Loading

            val state = _registerForm.value
            val validation = RegisterValidator.validate(
                email = state.email,
                username = state.username,
                password = state.password
            )

            _registerForm.value = state.copy(
                emailError = validation.emailError,
                usernameError = validation.passwordError,
                passwordError = validation.passwordError
            )

            if(!validation.isValid){
                _registerState.value = State.Idle
                return@launch
            }
            try {
                val result = authRepository.register(username = state.username, email = state.email, password = state.password)
                result.fold(
                    onSuccess = { data ->
                        _registerState.value = State.Success(Unit)
                    },
                    onFailure = { throwable ->
                        _registerState.value = State.Error(throwable.message ?: "Unkown Error")
                    }
                )

            } catch (e: Exception) {
                _registerState.value = State.Error(e.message ?: "Unknown error")
            }

        }
    }

}