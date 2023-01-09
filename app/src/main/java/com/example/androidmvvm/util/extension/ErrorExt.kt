package com.example.androidmvvm.util.extension

import android.content.Context
import com.example.androidmvvm.R
import com.example.androidmvvm.model.error.ApiError

fun Error.getErrorMessage(context: Context?): String {
    return when (this) {
        is ApiError.Connection -> context?.getString(R.string.msg_no_internet_error)
        is ApiError.Server -> errorMessage
        else -> context?.getString(R.string.msg_unknown_error)
    }.defaultEmpty()
}
