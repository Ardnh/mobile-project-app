package com.example.mobileprojectapp.presentation.features.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.repository.AuthRepository
import com.example.mobileprojectapp.presentation.features.login.LoginState
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
    private val _registerState = MutableStateFlow<State<LoginState>>(State.Idle)
    val registerState: StateFlow<State<LoginState>> = _registerState.asStateFlow()

    // -----------------------------
    // UI Event Actions
    // -----------------------------


    // -----------------------------
    // API Actions
    // -----------------------------
    fun register(){
        viewModelScope.launch {

        }
    }

}