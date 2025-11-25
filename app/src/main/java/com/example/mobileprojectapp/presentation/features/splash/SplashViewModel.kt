package com.example.mobileprojectapp.presentation.features.splash

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileprojectapp.domain.repository.AuthRepository
import com.example.mobileprojectapp.domain.repository.UserRepository
import com.example.mobileprojectapp.presentation.navigation.NavigationEvent
import com.example.mobileprojectapp.utils.SecureStorageManager
import com.example.mobileprojectapp.utils.isExpired
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor( private val storage: SecureStorageManager) : ViewModel() {

    // -----------------------------
    // UI State
    // -----------------------------
    // State untuk auto-login check
    private val _isCheckingAuth = MutableStateFlow(false)
    val isCheckingAuth = _isCheckingAuth.asStateFlow()
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>(replay = 1)
    val navigationEvent = _navigationEvent.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkExistingAuth() {
        viewModelScope.launch {
            _isCheckingAuth.value = true
            Log.d("CheckAuth", "=== START CHECKING AUTH ===")

            try {
                val token = storage.getAuthToken()
                val expiryTime = storage.getAuthTokenExpireDate()

                Log.d("CheckAuth", "Token: ${token?.take(20)}...")
                Log.d("CheckAuth", "Expiry Time: $expiryTime")

                if (token.isNullOrEmpty() || expiryTime == null) {
                    Log.d("CheckAuth", "❌ Token or expiry is null/empty")
                    return@launch
                }

                val expired = isExpired(expiryTime)
                Log.d("CheckAuth", "Token expired: $expired")

                if (expiryTime.isNotEmpty() && expired) {
                    Log.d("CheckAuth", "❌ Token is expired - clearing data")
                    storage.clearAuthData()
                    return@launch
                }

                Log.d("CheckAuth", "✅ Token is VALID - Emitting navigation event")
                _navigationEvent.emit(NavigationEvent.NavigateToHome)
                Log.d("CheckAuth", "✅ Navigation event EMITTED")

            } catch (e: Exception) {
                Log.e("CheckAuth", "❌ Exception occurred", e)
            } finally {
                _isCheckingAuth.value = false
                Log.d("CheckAuth", "=== END CHECKING AUTH (isChecking = false) ===")
            }
        }
    }

}
