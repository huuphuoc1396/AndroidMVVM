package com.example.androidmvvm.ui.util.extension

import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

fun AppCompatImageView.setImageUrl(url: String?, @DrawableRes placeholder: Int? = null) {
    val placeholderDrawable = placeholder?.let {
        AppCompatResources.getDrawable(context, placeholder)
    }
    if (placeholderDrawable == null) {
        Glide.with(context)
            .load(url)
            .into(this)
    } else {
        Glide.with(context)
            .load(url)
            .placeholder(placeholderDrawable)
            .into(this)
    }
}