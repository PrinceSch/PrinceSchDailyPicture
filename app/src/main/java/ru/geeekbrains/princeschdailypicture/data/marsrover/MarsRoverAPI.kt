package ru.geeekbrains.princeschdailypicture.data.marsrover

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface  MarsRoverAPI {

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsRoverByDate(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsRoverServerResponseData>

}