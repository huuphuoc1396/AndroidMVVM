package com.example.androidmvvm.core.error

interface FailureHandler {
    fun handleThrowable(throwable: Throwable): Failure?
}