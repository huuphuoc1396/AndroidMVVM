package com.example.androidmvvm.remote.error

import com.example.androidmvvm.model.error.ApiError
import com.example.androidmvvm.model.functional.ErrorHandler
import com.example.androidmvvm.util.extension.default
import com.example.androidmvvm.util.extension.defaultEmpty
import com.example.androidmvvm.remote.response.ServerErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class RemoteErrorHandler @Inject constructor() : ErrorHandler {
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