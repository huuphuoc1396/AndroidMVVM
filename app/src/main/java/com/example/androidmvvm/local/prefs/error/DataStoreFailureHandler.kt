package com.example.androidmvvm.local.prefs.error

import com.example.androidmvvm.core.error.DataStoreFailure
import com.example.androidmvvm.core.error.FailureHandler
import java.io.IOException
import javax.inject.Inject

class DataStoreFailureHandler @Inject constructor() : FailureHandler {
    override fun handleThrowable(throwable: Throwable) = when (throwable) {
        is IOException -> DataStoreFailure.IOFailure
        else -> null
    }
}