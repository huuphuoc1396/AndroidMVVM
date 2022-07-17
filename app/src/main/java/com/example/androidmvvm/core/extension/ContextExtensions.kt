package com.example.androidmvvm.core.extension

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.toast(resId: Int, length: Int = Toast.LENGTH_SHORT) {
    toast(getString(resId), length)
}