package ru.geeekbrains.princeschdailypicture.repository

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    object Loading : PictureOfTheDayData()
}
