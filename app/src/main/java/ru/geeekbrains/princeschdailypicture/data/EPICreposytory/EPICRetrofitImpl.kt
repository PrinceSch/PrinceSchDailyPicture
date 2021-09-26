package ru.geeekbrains.princeschdailypicture.data.EPICreposytory

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geeekbrains.princeschdailypicture.BuildConfig

class EPICRetrofitImpl {

    private val baseUrl = "https://api.nasa.gov/"

    private val epicAPI = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build().create(EPICapi::class.java)

    fun getEpicAPI(date: String, callback: Callback<List<EpicServerResponseData>>){
        epicAPI.getEpicData(date, BuildConfig.NASA_API_KEY).enqueue(callback)
    }
}