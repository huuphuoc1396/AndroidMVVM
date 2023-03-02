package com.example.androidmvvm.data.remote.retrofit.adapter

import com.example.androidmvvm.domain.model.exception.RemoteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FlowCallAdapter<T>(
    private val retrofit: Retrofit,
    private val responseType: Type
) : CallAdapter<T, Flow<T>> {
    override fun adapt(call: Call<T>): Flow<T> {
        return flow {
            emit(
                suspendCancellableCoroutine { continuation ->
                    call.enqueue(object : Callback<T> {
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            continuation.resumeWithException(asRemoteException(t))
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            try {
                                continuation.resume(response.body()!!)
                            } catch (e: Exception) {
                                continuation.resumeWithException(asRemoteException(e, response))
                            }
                        }
                    })
                    continuation.invokeOnCancellation { call.cancel() }
                }
            )
        }
    }

    override fun responseType() = responseType

    private fun asRemoteException(
        throwable: Throwable,
        res: Response<T>? = null
    ): RemoteException {
        // We had non-200 http error
        if (throwable is HttpException) {
            val response = throwable.response()

            return when (throwable.code()) {
                422 -> // on out api 422's get metadata in the response. Adjust logic here based on your needs
                    RemoteException.httpErrorWithObject(
                        url = response?.raw()?.request?.url.toString(),
                        response = response,
                        retrofit = retrofit,
                    )
                else -> RemoteException.httpError(
                    url = response?.raw()?.request?.url.toString(),
                    response = response,
                    retrofit = retrofit,
                )
            }
        }

        if (res != null) {
            return RemoteException.httpErrorWithObject(
                url = res.raw().request.url.toString(),
                response = res,
                retrofit = retrofit,
            )
        }

        // A network error happened
        if (throwable is IOException) {
            return RemoteException.networkError(throwable)
        }

        // We don't know what happened. We need to simply convert to an unknown error
        return RemoteException.unexpectedError(throwable)
    }
}
