package ru.geeekbrains.princeschdailypicture.data

import ru.geeekbrains.princeschdailypicture.data.EPICreposytory.EpicServerResponseData
import ru.geeekbrains.princeschdailypicture.data.PODrepository.PODServerResponseData
import ru.geeekbrains.princeschdailypicture.data.marsrover.MarsRoverServerResponseData

sealed class AppState {
    data class SuccessPOD(val serverResponseData: PODServerResponseData) : AppState()
    data class SuccessEPIC(val serverResponseDataEPIC: List<EpicServerResponseData>) : AppState()
    data class SuccessMarsRover(val serverResponseDataMR: MarsRoverServerResponseData) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
