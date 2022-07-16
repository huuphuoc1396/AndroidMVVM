package com.example.androidmvvm.core.error

data class DefaultFailure(
    val exception: Exception,
) : Failure