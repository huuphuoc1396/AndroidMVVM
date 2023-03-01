package com.example.androidmvvm.ui.feature.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.example.androidmvvm.databinding.DialogMessageBinding
import com.example.androidmvvm.domain.util.defaultEmpty
import com.example.androidmvvm.ui.platform.BaseFullScreenDialogFragment
import com.example.androidmvvm.ui.util.extension.setOnSingleClickListener
import com.example.androidmvvm.ui.util.extension.visibleOrGone

class MessageDialogFragment : BaseFullScreenDialogFragment<DialogMessageBinding>() {

    private var positiveTitle = ""
    private var negativeTitle = ""
    private var onPositiveClickListener: () -> Unit = {}
    private var onNegativeClickListener: () -> Unit = {}

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogMessageBinding {
        return DialogMessageBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(viewBinding) {
        txtMessage.text = getErrorMessage()
        btnPositive.text = positiveTitle
        btnPositive.visibleOrGone(positiveTitle.isNotEmpty())
        btnPositive.setOnSingleClickListener {
            onPositiveClickListener()
            dismiss()
        }

        btnNegative.text = negativeTitle
        btnNegative.visibleOrGone(negativeTitle.isNotEmpty())
        btnNegative.setOnSingleClickListener {
            onNegativeClickListener()
            dismiss()
        }

        vDivider.visibleOrGone(btnPositive.isVisible && btnNegative.isVisible)
    }

    fun setPositiveButton(
        title: String,
        onClickListener: () -> Unit = {}
    ): MessageDialogFragment {
        positiveTitle = title
        onPositiveClickListener = onClickListener
        return this
    }

    fun setNegativeButton(
        title: String,
        onClickListener: () -> Unit
    ): MessageDialogFragment {
        negativeTitle = title
        onNegativeClickListener = onClickListener
        return this
    }

    private fun getErrorMessage(): String {
        return arguments?.getString(KEY_MESSAGE).defaultEmpty()
    }

    companion object {

        const val TAG = "MessageDialogFragment"

        private const val KEY_MESSAGE = "KEY_MESSAGE"

        fun newInstance(message: String): MessageDialogFragment {
            val fragment = MessageDialogFragment()
            fragment.arguments = bundleOf(
                KEY_MESSAGE to message
            )
            return fragment
        }
    }
}