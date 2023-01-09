package com.example.androidmvvm.util.extension

import androidx.fragment.app.FragmentTransaction
import com.example.androidmvvm.R

fun FragmentTransaction.crossFade() {
    setCustomAnimations(
        R.anim.fade_in,
        R.anim.fade_out,
        R.anim.fade_in,
        R.anim.fade_out
    )
}

fun FragmentTransaction.slideFade() {
    setCustomAnimations(
        R.anim.slide_in_right,
        R.anim.slide_out_left,
        R.anim.slide_in_left,
        R.anim.slide_out_right,
    )
}

