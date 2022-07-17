package com.example.androidmvvm.local.db.error

import com.example.androidmvvm.core.error.DbFailure
import com.example.androidmvvm.core.error.FailureHandler
import java.io.IOException
import javax.inject.Inject

class DbFailureHandler @Inject constructor() : FailureHandler {
    override fun handleThrowable(throwable: Throwable) = when (throwable) {
        is IOException -> DbFailure.IOFailure
        else -> null
    }
}