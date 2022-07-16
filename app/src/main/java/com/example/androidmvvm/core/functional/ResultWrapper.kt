/**
 * Copyright (C) 2019 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androidmvvm.core.functional

import com.example.androidmvvm.core.error.Failure
import com.example.androidmvvm.core.functional.ResultWrapper.Error
import com.example.androidmvvm.core.functional.ResultWrapper.Success

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [ResultWrapper] are either an instance of [Error] or [Success].
 * FP Convention dictates that [Error] is used for "error"
 * and [Success] is used for "success".
 *
 * @see Error
 * @see Success
 */
sealed class ResultWrapper<out R> {
    /** * Represents the error side of [ResultWrapper] class which by convention is a "Error". */
    data class Error(val failure: Failure) : ResultWrapper<Nothing>()

    /** * Represents the success side of [ResultWrapper] class which by convention is a "Success". */
    data class Success<out R>(val result: R) : ResultWrapper<R>()

    /**
     * Returns true if this is a Success, false otherwise.
     * @see Success
     */
    val isSuccess get() = this is Success<R>

    /**
     * Returns true if this is a Error, false otherwise.
     * @see Error
     */
    val isError get() = this is Error

    /**
     * Creates a Error type.
     * @see Error
     */
    fun failure(failure: Failure) = Error(failure)


    /**
     * Creates a Success type.
     * @see Success
     */
    fun <R> success(b: R) = Success(b)

    /**
     * Applies fnL if this is a Error or fnR if this is a Success.
     * @see Error
     * @see Success
     */
    fun fold(fnL: (Failure) -> Unit, fnR: (R) -> Unit): Unit = when (this) {
        is Error -> fnL(failure)
        is Success -> fnR(result)
    }
}

/**
 * Composes 2 functions
 * See <a href="https://proandroiddev.com/kotlins-nothing-type-946de7d464fb">Credits to Alex Hart.</a>
 */
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

/**
 * Success-biased flatMap() FP convention which means that Success is assumed to be the default case
 * to operate on. If it is Error, operations like map, flatMap, ... return the Error value unchanged.
 */
fun <T, R> ResultWrapper<R>.flatMap(fn: (R) -> ResultWrapper<T>): ResultWrapper<T> =
    when (this) {
        is Error -> Error(failure)
        is Success -> fn(result)
    }

/**
 * Success-biased map() FP convention which means that Success is assumed to be the default case
 * to operate on. If it is Error, operations like map, flatMap, ... return the Error value unchanged.
 */
fun <T, R> ResultWrapper<R>.map(fn: (R) -> (T)): ResultWrapper<T> =
    this.flatMap(fn.c(::success))

/** Returns the value from this `Success` or the given argument if this is a `Error`.
 *  Success(12).getOrElse(17) RETURNS 12 and Failure(12).getOrElse(17) RETURNS 17
 */
fun <R> ResultWrapper<R>.getOrElse(value: R): R =
    when (this) {
        is Error -> value
        is Success -> result
    }

/** Returns the value from this `Success` or `null` if this is a `Error`.
 *  Success(12).getOrNull() RETURNS 12 and Error(12).getOrNull() RETURNS `null`
 */
fun <R> ResultWrapper<R>.getOrNull(): R? =
    when (this) {
        is Error -> null
        is Success -> result
    }

/**
 * Error-biased onError() FP convention dictates that when this class is Error, it'll perform
 * the onError functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <R> ResultWrapper<R>.onError(fn: (failure: Failure) -> Unit): ResultWrapper<R> =
    this.apply { if (this is Error) fn(failure) }

/**
 * Success-biased onSuccess() FP convention dictates that when this class is Success, it'll perform
 * the onSuccess functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <R> ResultWrapper<R>.onSuccess(fn: (success: R) -> Unit): ResultWrapper<R> =
    this.apply { if (this is Success) fn(result) }
