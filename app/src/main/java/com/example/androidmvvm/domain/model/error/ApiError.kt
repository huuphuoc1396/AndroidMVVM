package com.example.androidmvvm.domain.model.error

sealed class ApiError : Error() {

    object Connection : ApiError()

    data class Server(
        val code: Int,
        val errorMessage: String,
    ) : ApiError()

    object Unauthorized : ApiError()
}