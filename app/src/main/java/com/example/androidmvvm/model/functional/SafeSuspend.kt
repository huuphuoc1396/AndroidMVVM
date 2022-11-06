package com.example.androidmvvm.model.functional

import com.example.androidmvvm.model.error.DefaultError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <R> safeSuspend(
    vararg errorHandlers: ErrorHandler,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    action: suspend () -> ResultWrapper<R>,
): ResultWrapper<R> = withContext(dispatcher) {
    try {
        action()
    } catch (exception: Exception) {
        errorHandlers.forEach { failureHandler ->
            val failure = failureHandler.handleThrowable(exception)
            if (failure != null) {
                ResultWrapper.Failure(failure)
            }
        }
        ResultWrapper.Failure(DefaultError(exception))
    }
}

suspend fun <R> safeSuspendIgnoreFailure(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    action: suspend () -> R,
): R? = withContext(dispatcher) {
    try {
        action()
    } catch (exception: Exception) {
        null
    }
}