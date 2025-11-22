package com.example.mobileprojectapp.presentation.navigation

sealed class NavigationEvent {
    object NavigateToHome : NavigationEvent()
    object NavigateToLogin : NavigationEvent()
    object NavigateBack : NavigationEvent()
    data class NavigateToDetail(val id: String) : NavigationEvent()
}