package com.example.androidmvvm.domain.model.error

data class UnknownError(
    val exception: Exception,
) : Error()