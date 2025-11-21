package com.example.mobileprojectapp.presentation.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.repository.AuthRepository
import com.example.mobileprojectapp.utils.LoginValidator
import com.example.mobileprojectapp.utils.SecureStorageManager
import com.example.mobileprojectapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository, private val storage: SecureStorageManager) : ViewModel() {

    // -----------------------------
    // UI State
    // -----------------------------
    private val _loginForm = MutableStateFlow(LoginState())
    val loginForm = _loginForm.asStateFlow()
    // -----------------------------
    // API Result State
    // -----------------------------
    private val _loginResult = MutableStateFlow<State<Unit>>(State.Idle)
    val loginResult: StateFlow<State<Unit>> = _loginResult

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

            if (!validation.isValid) return@launch
            try {
                val result =  authRepository.login(state.username, state.password)
                result.fold(
                    onSuccess = { data ->
                        storage.saveAuthToken(data.token)
                        _loginResult.value = State.Success(Unit)
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