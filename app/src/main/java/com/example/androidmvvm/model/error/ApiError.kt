package com.example.androidmvvm.model.error

sealed class ApiError : Error() {

    object Connection : ApiError()

    data class Server(
        val code: Int,
        val errorMessage: String,
    ) : ApiError()
}