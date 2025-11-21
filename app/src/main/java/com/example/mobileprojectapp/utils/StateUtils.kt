package com.example.mobileprojectapp.utils

sealed class State <out T> {
    data object Idle : State<Nothing>()
    data object Loading : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
    data class Error(val message: String) : State<Nothing>()
}