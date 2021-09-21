package ru.geeekbrains.princeschdailypicture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geeekbrains.princeschdailypicture.data.AppState
import ru.geeekbrains.princeschdailypicture.data.marsrover.MarsRoverRetrofitImpl
import ru.geeekbrains.princeschdailypicture.data.marsrover.MarsRoverServerResponseData

class MarsRoverViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: MarsRoverRetrofitImpl = MarsRoverRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<AppState> = liveDataToObserve

    fun getMarsRoverPhotosFromServer(date: String){
        liveDataToObserve.value = AppState.Loading
        retrofitImpl.getMarsPictureByDate(date, callBack)
    }

    private val callBack = object : Callback<MarsRoverServerResponseData>{
        override fun onResponse(
            call: Call<MarsRoverServerResponseData>,
            response: Response<MarsRoverServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.value = AppState.SuccessMarsRover(response.body()!!)
            } else {
                val message = response.message()
                liveDataToObserve.postValue(AppState.Error(Throwable(message)))
            }
        }

        override fun onFailure(call: Call<MarsRoverServerResponseData>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(t))
        }
    }

}