package com.example.androidmvvm.core.error

sealed class DbFailure : Failure {

    object IOFailure : DbFailure()
}