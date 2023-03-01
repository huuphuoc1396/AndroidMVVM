package com.example.androidmvvm.domain.model.functional

interface ErrorHandler {
    fun handleThrowable(throwable: Throwable): Error?
}