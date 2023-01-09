package com.example.androidmvvm.model.functional

import com.example.androidmvvm.model.error.UnknownError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

suspend fun <R> safeSuspend(
    vararg errorHandlers: ErrorHandler,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    action: suspend () -> ResultWrapper<R>,
): ResultWrapper<R> = withContext(dispatcher) {
    try {
        action()
    } catch (exception: Exception) {
        Timber.e(exception)
        errorHandlers.forEach { failureHandler ->
            val failure = failureHandler.handleThrowable(exception)
            if (failure != null) {
                return@withContext ResultWrapper.Failure(failure)
            }
        }
        return@withContext ResultWrapper.Failure(UnknownError(exception))
    }
}

suspend fun <R> safeSuspendIgnoreFailure(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    action: suspend () -> R,
): R? = withContext(dispatcher) {
    try {
        action()
    } catch (exception: Exception) {
        Timber.e(exception)
        null
    }
}