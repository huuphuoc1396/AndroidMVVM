package com.example.androidmvvm.core.error

sealed class DataStoreFailure : Failure {

    object IOFailure : DataStoreFailure()
}