package com.example.androidmvvm.remote.response

import com.google.gson.annotations.SerializedName

data class ServerErrorResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
)