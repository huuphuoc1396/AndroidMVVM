package com.example.androidmvvm.remote.response

import com.google.gson.annotations.SerializedName

class BaseResponse {
    @SerializedName("status")
    val status: Int = 0

    @SerializedName("msg")
    val msg: String = ""
}