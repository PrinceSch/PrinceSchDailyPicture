package ru.geeekbrains.princeschdailypicture.data.EPICreposytory

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.geeekbrains.princeschdailypicture.BuildConfig

interface EPICapi {

    @GET("EPIC/api/natural/date/{date}?")
    fun getEpicData(
        @Path("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<List<EpicServerResponseData>>

}