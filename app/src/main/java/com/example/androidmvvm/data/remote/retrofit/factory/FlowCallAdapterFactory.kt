package com.example.androidmvvm.data.remote.retrofit.factory

import com.example.androidmvvm.data.remote.retrofit.adapter.FlowCallAdapter
import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class FlowCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // Check api service not use the Flow class
        if (getRawType(returnType) != Flow::class.java) {
            return null
        }

        check(returnType is ParameterizedType) { "Flow return type must be parameterized as Flow<Foo> or Flow<out Foo>" }
        val responseType = getParameterUpperBound(0, returnType)

        return FlowCallAdapter<Any>(retrofit, responseType)
    }

    companion object {
        @JvmStatic
        fun create() = FlowCallAdapterFactory()
    }
}
