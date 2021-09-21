package ru.geeekbrains.princeschdailypicture.data.marsrover

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geeekbrains.princeschdailypicture.BuildConfig

class MarsRoverRetrofitImpl {

    private val baseUrl = "https://api.nasa.gov/"

    private val marsRoverAPI = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build().create(MarsRoverAPI::class.java)

    fun getMarsPictureByDate(earth_date: String, marsCallbackByDate: Callback<MarsRoverServerResponseData>) {
        marsRoverAPI.getMarsRoverByDate(earth_date, BuildConfig.NASA_API_KEY).enqueue(marsCallbackByDate)
    }

}