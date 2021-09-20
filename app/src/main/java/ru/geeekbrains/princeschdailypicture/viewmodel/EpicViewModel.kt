package ru.geeekbrains.princeschdailypicture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geeekbrains.princeschdailypicture.data.AppState
import ru.geeekbrains.princeschdailypicture.data.EPICreposytory.EPICRetrofitImpl
import ru.geeekbrains.princeschdailypicture.data.EPICreposytory.EpicServerResponseData

private const val CORRUPTED_DATA = "Неполные данные"

class EpicViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: EPICRetrofitImpl = EPICRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<AppState> = liveDataToObserve

    fun getEpicDataFromServer(date: String){
        liveDataToObserve.postValue(AppState.Loading)
        retrofitImpl.getEpicAPI(date, callBack)
    }

    private val callBack = object : Callback<EpicServerResponseData>{
        override fun onResponse(
            call: Call<EpicServerResponseData>,
            response: Response<EpicServerResponseData>
        ) {
            val serverResponse: EpicServerResponseData? = response.body()
            if (response.isSuccessful && serverResponse != null) {
                liveDataToObserve.postValue(AppState.Success(null, response.body() as EpicServerResponseData))
            } else {
                liveDataToObserve.postValue(AppState.Error(Throwable("response error")))
            }
        }

        override fun onFailure(call: Call<EpicServerResponseData>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(t.message)))
        }
    }

}