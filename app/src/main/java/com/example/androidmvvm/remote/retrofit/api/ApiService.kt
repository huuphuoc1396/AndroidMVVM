package com.example.androidmvvm.remote.retrofit.api

import com.example.androidmvvm.remote.response.BaseResponse
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getSomething(): BaseResponse
}