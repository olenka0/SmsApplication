package com.example.smsapplication.data.requestModel

import com.google.gson.annotations.SerializedName

data class SendInfoRequestModel(
     val user_name: String,
     val password: String?,
     val from_number: String?,
     val to_number: String?,
     val text: String?
)

