package com.example.androidmvvm.local.db.error

import com.example.androidmvvm.core.error.DbFailure
import java.io.IOException
import javax.inject.Inject

class DbFailureHandler @Inject constructor() {
    fun handleThrowable(throwable: Throwable) = when (throwable) {
        is IOException -> DbFailure.IOFailure
        else -> null
    }
}