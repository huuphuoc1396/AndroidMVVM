package com.example.androidmvvm.model.error

data class DefaultError(
    val exception: Exception,
) : Error()