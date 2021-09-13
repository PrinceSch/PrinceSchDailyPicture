package ru.geeekbrains.princeschdailypicture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geeekbrains.princeschdailypicture.BuildConfig
import ru.geeekbrains.princeschdailypicture.repository.PODRetrofitImpl
import ru.geeekbrains.princeschdailypicture.repository.PODServerResponseData
import ru.geeekbrains.princeschdailypicture.repository.PictureOfTheDayData

class MainViewModel (private val liveDataToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
                     private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()): ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheDayData> {
        return liveDataToObserve
    }

    fun sendServerRequest(){
        liveDataToObserve.postValue(PictureOfTheDayData.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if(apiKey.isBlank()){
            PictureOfTheDayData.Error(Throwable("You need API key"))
        }else{
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(
                object : Callback<PODServerResponseData> {
                    override fun onResponse(
                        call: Call<PODServerResponseData>,
                        response: Response<PODServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataToObserve.postValue(PictureOfTheDayData.Success(response.body() as PODServerResponseData))
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveDataToObserve.value =
                                    PictureOfTheDayData.Error(Throwable("Unidentified error"))
                            }
                        }
                    }

                    override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                        liveDataToObserve.value = PictureOfTheDayData.Error(t)
                    }

                }
            )
        }
    }

}