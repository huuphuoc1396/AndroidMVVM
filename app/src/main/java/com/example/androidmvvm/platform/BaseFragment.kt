package com.example.androidmvvm.platform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.androidmvvm.R
import com.example.androidmvvm.feature.dialog.ErrorDialogFragment
import com.example.androidmvvm.feature.dialog.LoadingDialogFragment
import com.example.androidmvvm.model.error.ApiError
import com.example.androidmvvm.util.extension.dismissIfAdded
import com.example.androidmvvm.util.extension.isAvailable
import com.example.androidmvvm.util.extension.showIfNotExist
import com.example.androidmvvm.util.livedata.autoCleared
import timber.log.Timber

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    abstract val viewModel: VM?

    abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    open fun onBackPressed(): Boolean = true

    var viewBinding: VB by autoCleared()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (onBackPressed()) {
                isEnabled = false
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    private var loadingDialogFragment: LoadingDialogFragment? = null

    override fun onAttach(context: Context) {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onCreate")
        super.onCreate(savedInstanceState)
        addBackPressedCallback()
        observeBaseViewModel()
    }

    private fun observeBaseViewModel() {
        val baseViewModel = viewModel as? BaseViewModel ?: return
        baseViewModel.error.observe(this) { failure ->
            onError(failure)
        }

        baseViewModel.isLoading.observe(this) { isLoading ->
            onLoading(isLoading)
        }
    }

    private fun addBackPressedCallback() {
        activity?.onBackPressedDispatcher?.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onCreateView")
        viewBinding = onCreateViewBinding(inflater, container)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onStart")
        super.onStart()

        onBackPressedCallback.isEnabled = true
    }

    override fun onResume() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onResume")
        super.onResume()
    }

    override fun onPause() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onDetach")
        super.onDetach()
    }

    open fun onError(error: Error) {
        val message = when (error) {
            is ApiError.Connection -> {
                getString(R.string.msg_no_internet_error)
            }
            is ApiError.Server -> {
                error.errorMessage.ifEmpty {
                    getString(R.string.msg_unexpected_error)
                }
            }
            else -> {
                getString(R.string.msg_unknown_error)
            }
        }
        ErrorDialogFragment.newInstance(message).showIfNotExist(
            fragmentManager = childFragmentManager,
            tag = ErrorDialogFragment.TAG,
        )
    }

    open fun onLoading(isLoading: Boolean) {
        if (isLoading && activity.isAvailable()) {
            if (loadingDialogFragment == null) {
                loadingDialogFragment = LoadingDialogFragment.newInstance()
            }
            loadingDialogFragment?.showIfNotExist(childFragmentManager, LoadingDialogFragment.TAG)
        } else {
            loadingDialogFragment?.dismissIfAdded()
            loadingDialogFragment = null
        }
    }

    companion object {
        private const val LIFECYCLE_TAG = "FragmentLifecycle"
    }
}