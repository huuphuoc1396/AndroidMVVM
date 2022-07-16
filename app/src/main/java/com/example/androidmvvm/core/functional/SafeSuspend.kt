package com.example.androidmvvm.core.functional

import com.example.androidmvvm.core.error.DefaultFailure
import com.example.androidmvvm.core.error.FailureHandler

suspend fun <R> safeSuspend(
    vararg failureHandlers: FailureHandler,
    action: suspend () -> ResultWrapper<R>
): ResultWrapper<R> = try {
    action()
} catch (exception: Exception) {
    failureHandlers.forEach { failureHandler ->
        val failure = failureHandler.handleThrowable(exception)
        if (failure != null) {
            return ResultWrapper.Error(failure)
        }
    }
    ResultWrapper.Error(DefaultFailure(exception))
}

suspend fun <R> safeSuspendIgnoreFailure(
    action: suspend () -> R
): R? = try {
    action()
} catch (exception: Exception) {
    null
}