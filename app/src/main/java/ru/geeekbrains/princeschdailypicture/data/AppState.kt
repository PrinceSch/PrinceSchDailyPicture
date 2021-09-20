package ru.geeekbrains.princeschdailypicture.data

import ru.geeekbrains.princeschdailypicture.data.EPICreposytory.EpicServerResponseData
import ru.geeekbrains.princeschdailypicture.data.PODrepository.PODServerResponseData

sealed class AppState {
    data class Success(
        val serverResponseDataPOD: PODServerResponseData?,
        val serverResponseDataEPIC: EpicServerResponseData?
    ) : AppState()

    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
