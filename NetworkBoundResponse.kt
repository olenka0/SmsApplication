package com.example.smsapplication.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.smsapplication.utils.RetrofitErrorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MediatorLiveData<ViewStateType>()

    init {
        try {
            GlobalScope.launch() {
                withContext(Main) {
                    val apiResponse = createCall()
                    result.addSource(apiResponse) { res ->
                        result.removeSource(apiResponse)

                        handleNetworkCall(res)
                    }
                }
            }
        }catch (e:Exception){
            Thread(Runnable{
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)

                    handleNetworkCall(response)
                }
            }).start()
        }
    }

    fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse -> {
                var error: String? = response.errorMessage
                if (!response.errorMessage.contains("401")) {
                    println("DEBUG: NetworkBoundResource: ${response.errorMessage}")
                    error = RetrofitErrorUtils.parseError(response.errorMessage)
                }
                onReturnError(error!!)
            }
            is ApiEmptyResponse -> {
                println("DEBUG: NetworkBoundResource: Request returned NOTHING (HTTP 204)")
                onReturnError("HTTP 204. Returned NOTHING.")
            }
        }

        fun asLiveData() = result as LiveData<ViewStateType>
    }

    abstract fun onReturnError(message: String)
    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)
    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>
    fun asLiveData() = result as LiveData<ViewStateType>

}