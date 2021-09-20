package ru.geeekbrains.princeschdailypicture.data.EPICreposytory

import com.google.gson.annotations.SerializedName

data class EpicServerResponseData(

    @field:SerializedName("identifier") val identifier: String?,
    @field:SerializedName("caption") val caption: String?,
    @field:SerializedName("image") val image: String?

)