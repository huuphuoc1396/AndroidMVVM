package com.example.androidmvvm.data.remote.error

import com.example.androidmvvm.data.remote.response.ServerErrorResponse
import com.example.androidmvvm.domain.model.error.ApiError
import com.example.androidmvvm.domain.model.functional.ErrorHandler
import com.example.androidmvvm.domain.util.default
import com.example.androidmvvm.domain.util.defaultEmpty
import com.google.gson.Gson
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class RemoteErrorHandler : ErrorHandler {
    override fun handleThrowable(throwable: Throwable) = when (throwable) {
        is IOException -> ApiError.Connection
        is HttpException -> handleHttpException(throwable)
        else -> null
    }

    private fun handleHttpException(httpException: HttpException): ApiError.Server {
        val code = httpException.code().default(-1)
        val errorBody = httpException.response()?.errorBody()?.string()
        val errorMessage = try {
            val serverErrorResponse = Gson().fromJson(errorBody, ServerErrorResponse::class.java)
            serverErrorResponse.message.defaultEmpty()
        } catch (parseException: Exception) {
            Timber.e(parseException)
            ""
        }
        return ApiError.Server(
            code = code,
            errorMessage = errorMessage
        )
    }
}