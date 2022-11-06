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
package com.example.androidmvvm.model.functional

import com.example.androidmvvm.model.functional.ResultWrapper.Failure
import com.example.androidmvvm.model.functional.ResultWrapper.Success

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [ResultWrapper] are either an instance of [Failure] or [Success].
 * FP Convention dictates that [Failure] is used for "failure"
 * and [Success] is used for "success".
 *
 * @see Failure
 * @see Success
 */
sealed class ResultWrapper<out R> {
    /** * Represents the error side of [ResultWrapper] class which by convention is a [Failure]. */
    data class Failure(val error: Error) : ResultWrapper<Nothing>()

    /** * Represents the success side of [ResultWrapper] class which by convention is [Success]. */
    data class Success<out R>(val result: R) : ResultWrapper<R>()

    /**
     * Returns true if this is a Success, false otherwise.
     * @see Success
     */
    val isSuccess get() = this is Success<R>

    /**
     * Returns true if this is a [Failure], false otherwise.
     * @see Failure
     */
    val isFailure get() = this is Failure

    /**
     * Creates a [Failure] type.
     * @see Failure
     */
    fun failure(failure: Error) = Failure(failure)


    /**
     * Creates a [Success] type.
     * @see Success
     */
    fun <R> success(b: R) = Success(b)

    /**
     * Applies fnL if this is a [Failure] or fnR if this is a [Success].
     * @see Failure
     * @see Success
     */
    fun fold(fnL: (Error) -> Unit, fnR: (R) -> Unit): Unit =
        when (this) {
            is Failure -> fnL(error)
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
        is Failure -> Failure(error)
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
        is Failure -> value
        is Success -> result
    }

/** Returns the value from this `Success` or `null` if this is a `Error`.
 *  Success(12).getOrNull() RETURNS 12 and Error(12).getOrNull() RETURNS `null`
 */
fun <R> ResultWrapper<R>.getOrNull(): R? =
    when (this) {
        is Failure -> null
        is Success -> result
    }

/**
 * Error-biased onError() FP convention dictates that when this class is Error, it'll perform
 * the onError functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <R> ResultWrapper<R>.onError(fn: (error: Error) -> Unit): ResultWrapper<R> =
    this.apply { if (this is Failure) fn(error) }

/**
 * Success-biased onSuccess() FP convention dictates that when this class is Success, it'll perform
 * the onSuccess functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <R> ResultWrapper<R>.onSuccess(fn: (success: R) -> Unit): ResultWrapper<R> =
    this.apply { if (this is Success) fn(result) }
