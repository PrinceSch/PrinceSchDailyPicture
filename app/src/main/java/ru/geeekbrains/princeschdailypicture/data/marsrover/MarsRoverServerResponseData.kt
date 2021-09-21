package ru.geeekbrains.princeschdailypicture.data.marsrover

import com.google.gson.annotations.SerializedName

data class MarsRoverServerResponseData (
    @field:SerializedName("photos") val photos: List<MarsRoverPhoto>
        )

data class MarsRoverPhoto(
    @field:SerializedName("img_src") val imgSrc: String?,
    @field:SerializedName("earth_date") val earth_date: String?
)
