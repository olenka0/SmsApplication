package com.example.smsapplication.api

import androidx.lifecycle.LiveData
import com.example.smsapplication.data.requestModel.SendInfoRequestModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("Credited.aspx")
    fun getSms(
        @Query("Username") Username:String,
        @Query("Password") Password:String,
        @Query("From") From:String,
        @Query("To") To:String,
        @Query("Text") text:String,

    ):LiveData<GenericApiResponse<SendInfoRequestModel>>
}