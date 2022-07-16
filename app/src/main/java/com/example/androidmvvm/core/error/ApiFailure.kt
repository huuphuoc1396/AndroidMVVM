package com.example.androidmvvm.core.error

sealed class ApiFailure : Failure {

    object Connection : ApiFailure()

    data class Server(
        val code: Int,
        val errorMessage: String,
    ) : ApiFailure()
}