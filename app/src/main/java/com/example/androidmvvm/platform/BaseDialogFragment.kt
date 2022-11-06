package com.example.androidmvvm.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.androidmvvm.R
import com.example.androidmvvm.util.livedata.autoCleared

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    var viewBinding: VB by autoCleared()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = onCreateViewBinding(inflater, container)
        return viewBinding.root
    }
}
