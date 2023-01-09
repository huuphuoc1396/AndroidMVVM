package com.example.androidmvvm.model.error

data class UnknownError(
    val exception: Exception,
) : Error()