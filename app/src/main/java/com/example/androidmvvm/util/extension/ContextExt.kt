package com.example.androidmvvm.util.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast


fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.toast(resId: Int, length: Int = Toast.LENGTH_SHORT) {
    toast(getString(resId), length)
}

fun Context.copyToClipboard(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    val clip = ClipData.newPlainText("", text)
    clipboard?.setPrimaryClip(clip)
}