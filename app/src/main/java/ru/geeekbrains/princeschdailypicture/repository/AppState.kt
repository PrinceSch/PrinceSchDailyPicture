package ru.geeekbrains.princeschdailypicture.repository

sealed class AppState {
    data class Success(val serverResponseData: PODServerResponseData) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
