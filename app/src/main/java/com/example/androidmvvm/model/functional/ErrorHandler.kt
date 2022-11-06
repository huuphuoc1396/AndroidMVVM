package com.example.androidmvvm.model.functional

interface ErrorHandler {
    fun handleThrowable(throwable: Throwable): Error?
}