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

    private val callBack = object : Callback<List<EpicServerResponseData>>{
        override fun onResponse(
            call: Call<List<EpicServerResponseData>>,
            response: Response<List<EpicServerResponseData>>
        ) {
            val serverResponse: List<EpicServerResponseData>? = response.body()
            if (response.isSuccessful && serverResponse != null) {
                liveDataToObserve.postValue(AppState.SuccessEPIC(response.body() as List<EpicServerResponseData>))
            } else {
                liveDataToObserve.postValue(AppState.Error(Throwable("response error")))
            }
        }

        override fun onFailure(call: Call<List<EpicServerResponseData>>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(t.message)))
        }
    }

    fun checkResponse(response: Response<List<EpicServerResponseData>>, serverResponse: List<EpicServerResponseData>?): Boolean{
        return response.isSuccessful && serverResponse != null
    }

}