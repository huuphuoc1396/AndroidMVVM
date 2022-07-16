package com.example.androidmvvm.remote.error

import com.example.androidmvvm.core.error.ApiFailure
import com.example.androidmvvm.core.extension.default
import com.example.androidmvvm.core.extension.defaultEmpty
import com.example.androidmvvm.remote.response.ServerErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class RemoteFailureHandler @Inject constructor() {
    fun handleThrowable(throwable: Throwable) = when (throwable) {
        is IOException -> ApiFailure.Connection
        is HttpException -> handleHttpException(throwable)
        else -> null
    }

    private fun handleHttpException(httpException: HttpException): ApiFailure.Server {
        val code = httpException.code().default(-1)
        val errorBody = httpException.response()?.errorBody()?.string()
        val errorMessage = try {
            val serverErrorResponse = Gson().fromJson(errorBody, ServerErrorResponse::class.java)
            serverErrorResponse.message.defaultEmpty()
        } catch (parseException: Exception) {
            Timber.e(parseException)
            ""
        }
        return ApiFailure.Server(
            code = code,
            errorMessage = errorMessage
        )
    }
}