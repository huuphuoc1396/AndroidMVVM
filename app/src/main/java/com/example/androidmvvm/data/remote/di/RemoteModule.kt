package com.example.androidmvvm.data.remote.di

import com.example.androidmvvm.BuildConfig
import com.example.androidmvvm.data.remote.retrofit.api.ApiService
import com.example.androidmvvm.data.remote.retrofit.builder.DefaultRetrofitBuilder
import com.example.androidmvvm.data.remote.retrofit.interceptor.HeaderInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val remoteModule = module {

    factory { HeaderInterceptor() }

    factory { DefaultRetrofitBuilder() }

    single {
        get<DefaultRetrofitBuilder>().baseUrl("https://api.github.com")
            .enableLogging(BuildConfig.DEBUG)
            .enableChucker(androidContext(), BuildConfig.DEBUG)
            .addInterceptor(get<HeaderInterceptor>())
            .build()
            .create(ApiService::class.java)
    }
}