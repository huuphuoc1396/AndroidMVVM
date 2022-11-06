package com.example.androidmvvm.util.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun Fragment.addFragment(
    fragment: Fragment,
    containerId: Int,
    addToBackStack: Boolean = false,
    tag: String? = null,
) {
    childFragmentManager.inTransaction {
        add(containerId, fragment, tag)
        if (addToBackStack) {
            addToBackStack(null)
        }
    }
}

fun Fragment.replaceFragment(
    fragment: Fragment,
    containerId: Int,
    addToBackStack: Boolean = false,
    tag: String? = null,
) {
    childFragmentManager.inTransaction {
        replace(containerId, fragment, tag)
        if (addToBackStack) {
            addToBackStack(null)
        }
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

