package com.example.androidmvvm.ui.util

import android.content.Context
import java.io.IOException

fun readFile(context: Context, fileName: String): String {
    return try {
        val inputStream = context.assets.open(fileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, charset("UTF-8"))
    } catch (ex: IOException) {
        ""
    }
}