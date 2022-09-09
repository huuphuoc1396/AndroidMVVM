package com.example.androidmvvm.core.extension

import android.view.View
import com.example.androidmvvm.core.listener.OnSingleClickListener

fun View.setOnSingleClickListener(onClickListener: View.OnClickListener?) {
    onClickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

fun View.setOnSingleClickListener(onClickListener: (View) -> Unit) {
    setOnSingleClickListener(View.OnClickListener { view ->
        onClickListener(view)
    })
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visibleOrGone(isVisible: Boolean) {
    if (isVisible) {
        visible()
    } else {
        gone()
    }
}

fun View.visibleOrInvisible(isVisible: Boolean) {
    if (isVisible) {
        visible()
    } else {
        invisible()
    }
}