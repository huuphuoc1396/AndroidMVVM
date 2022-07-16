package com.example.androidmvvm.remote.di

import android.content.Context
import com.example.androidmvvm.BuildConfig
import com.example.androidmvvm.remote.retrofit.api.ApiService
import com.example.androidmvvm.remote.retrofit.builder.DefaultRetrofitBuilder
import com.example.androidmvvm.remote.retrofit.interceptor.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideApiService(
        @ApplicationContext
        context: Context,
        headerInterceptor: HeaderInterceptor,
        defaultRetrofitBuilder: DefaultRetrofitBuilder,
    ): ApiService {
        return defaultRetrofitBuilder.baseUrl("/")
            .enableLogging(BuildConfig.DEBUG)
            .enableChucker(context, BuildConfig.DEBUG)
            .addInterceptor(headerInterceptor)
            .build()
            .create(ApiService::class.java)
    }
}