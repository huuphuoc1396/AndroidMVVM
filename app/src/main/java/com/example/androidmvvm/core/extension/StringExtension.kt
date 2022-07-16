package com.example.androidmvvm.core.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun String.decryptImageBase64(): Bitmap? {
    val imageBytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun String.replaceValueSize(valueSizeMin: Int, valueSizeMax: Int): String {
    val replaceMin = this.replace("min_value", "$valueSizeMin")
    return replaceMin.replace("max_value", "$valueSizeMax")
}