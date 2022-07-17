package com.example.androidmvvm.core.platform

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.androidmvvm.R
import com.example.androidmvvm.core.extension.defaultEmpty
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return createErrorDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    private fun createErrorDialog(): AlertDialog {
        val message: String = getErrorMessage()
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.title_error)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.create()
    }

    private fun getErrorMessage(): String {
        return arguments?.getString(KEY_ERROR_MESSAGE).defaultEmpty()
    }

    companion object {

        const val TAG = "ErrorDialogFragment"

        private const val KEY_ERROR_MESSAGE = "KEY_ERROR_MESSAGE"

        fun newInstance(message: String): ErrorDialogFragment {
            val fragment = ErrorDialogFragment()
            fragment.arguments = bundleOf(
                KEY_ERROR_MESSAGE to message
            )
            return fragment
        }
    }
}