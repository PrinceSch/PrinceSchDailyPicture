package ru.geeekbrains.princeschdailypicture.data.PODrepository

import com.google.gson.GsonBuilder
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class PODRetrofitImpl {
    private val baseUrl = "https://api.nasa.gov/"

    fun getRetrofitImpl():PictureOfTheDayAPI{
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(PictureOfTheDayAPI::class.java)
        }
}