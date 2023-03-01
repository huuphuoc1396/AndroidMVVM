package com.example.androidmvvm.ui.platform

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.androidmvvm.ui.util.livedata.autoCleared
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<VB : ViewBinding> : BottomSheetDialogFragment() {

    private val baseFragment by lazy { parentFragment as? BaseFragment<*, *> }

    abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    var viewBinding: VB by autoCleared()
    var bottomSheetBehavior: BottomSheetBehavior<FrameLayout> by autoCleared()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also { baseDialog ->
            val dialog = baseDialog as BottomSheetDialog
            bottomSheetBehavior = dialog.behavior
        }
    }

    fun setFixedHeight() {
        val bottomSheetLayout =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val bottomSheetLayoutParams = bottomSheetLayout?.layoutParams
        bottomSheetLayoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheetLayout?.layoutParams = bottomSheetLayoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = onCreateViewBinding(inflater, container)
        return viewBinding.root
    }

    fun navigate(fragment: Fragment, clearedAll: Boolean = false) {
        baseFragment?.navigate(fragment, clearedAll)
    }

    fun navigateUp(upToTag: String? = null, includeMatch: Boolean = false) {
        baseFragment?.navigateUp(upToTag, includeMatch)
    }

    fun navigateUp() {
        baseFragment?.navigateUp()
    }

}
