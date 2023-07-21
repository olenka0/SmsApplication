package com.example.smsapplication.api

import com.example.smsapplication.R
import com.example.smsapplication.utils.MainApplication
import com.google.gson.annotations.SerializedName


class ApiError {
    @SerializedName("error")
    var error: HashMap<String, ArrayList<String>>? = null

    fun getError(): String {
        return if (error != null && error!!.size != 0) {
            var errorMessage = error!!.values.toTypedArray()[0].toString()
            errorMessage = errorMessage.replace("[", "")
            errorMessage = errorMessage.replace("]", "")
            errorMessage
        } else MainApplication.applicationContext().getString(R.string.check_connection)
    }
}