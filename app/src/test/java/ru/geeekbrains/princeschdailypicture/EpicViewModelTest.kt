package ru.geeekbrains.princeschdailypicture

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response
import ru.geeekbrains.princeschdailypicture.data.EPICreposytory.EpicServerResponseData
import ru.geeekbrains.princeschdailypicture.viewmodel.EpicViewModel

class EpicViewModelTest {

    @Test
    fun checkResponseTest_True(){
        val response: Response<List<EpicServerResponseData>> = Response.success(listOf(EpicServerResponseData("id", "cap", "img")))
        val serverResponse: List<EpicServerResponseData> = listOf(EpicServerResponseData("id", "cap", "img"))
        assertTrue(EpicViewModel().checkResponse(response, serverResponse))
    }

    @Test
    fun checkResponseTest_False(){
        val response: Response<List<EpicServerResponseData>> = Response.success(listOf(EpicServerResponseData("id", "cap", "img")))
        val serverResponse: List<EpicServerResponseData>? = null
        assertFalse(EpicViewModel().checkResponse(response, serverResponse))
    }

}