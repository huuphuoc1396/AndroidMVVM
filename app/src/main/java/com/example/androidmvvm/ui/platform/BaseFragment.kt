package com.example.androidmvvm.ui.platform

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
import com.example.androidmvvm.domain.model.exception.RemoteException
import com.example.androidmvvm.domain.util.defaultEmpty
import com.example.androidmvvm.domain.util.defaultFalse
import com.example.androidmvvm.ui.feature.MainActivity
import com.example.androidmvvm.ui.feature.dialog.LoadingDialogFragment
import com.example.androidmvvm.ui.feature.dialog.MessageDialogFragment
import com.example.androidmvvm.ui.util.extension.dismissIfAdded
import com.example.androidmvvm.ui.util.extension.dismissKeyboard
import com.example.androidmvvm.ui.util.extension.isAvailable
import com.example.androidmvvm.ui.util.extension.showIfNotExist
import com.example.androidmvvm.ui.util.livedata.autoCleared
import timber.log.Timber

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    private val mainActivity by lazy { activity as? MainActivity }
    private val navigator by lazy { mainActivity?.stackNavigator }

    abstract val viewModel: VM?
    private val baseViewModel by lazy { viewModel as? BaseViewModel }

    abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    open fun onBackPressed(): Boolean = true

    var viewBinding: VB by autoCleared()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (onBackPressed()) {
                isEnabled = false
                if (!navigator?.pop().defaultFalse()) {
                    activity?.moveTaskToBack(true)
                }
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
        baseViewModel?.loading?.observe(this) { isLoading ->
            onLoading(isLoading = isLoading)
        }
        baseViewModel?.error?.observe(this) { throwable ->
            onError(message = throwable.toErrorMessage())
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
        dismissLoading()
        dismissKeyboard()
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

    open fun onError(message: String) {
        MessageDialogFragment.newInstance(message)
            .setPositiveButton(getString(R.string.txt_Ok))
            .showIfNotExist(
                fragmentManager = childFragmentManager,
                tag = MessageDialogFragment.TAG,
            )
    }

    open fun onLoading(isLoading: Boolean) {
        if (isLoading && activity.isAvailable()) {
            showLoading()
        } else {
            dismissLoading()
        }
    }

    private fun showLoading() {
        if (loadingDialogFragment == null) {
            loadingDialogFragment = LoadingDialogFragment.newInstance()
        }
        activity?.supportFragmentManager?.let { fragmentManager ->
            loadingDialogFragment?.showIfNotExist(fragmentManager, LoadingDialogFragment.TAG)
        }
    }

    private fun dismissLoading() {
        loadingDialogFragment?.dismissIfAdded()
        loadingDialogFragment = null
    }

    fun navigate(fragment: Fragment, clearAll: Boolean = false) = navigator?.apply {
        if (clearAll) {
            clear(includeMatch = true)
        }
        push(fragment, fragment.javaClass.name)
    }

    fun navigateUp(upToTag: String? = null, includeMatch: Boolean = false) {
        navigator?.clear(upToTag, includeMatch)
    }

    fun navigateUp() {
        navigator?.pop()
    }

    fun runWithPermission(
        permission: String,
        rationaleMessage: String,
        onPermissionGranted: () -> Unit = {},
        onPermissionDenied: () -> Unit = {}
    ) {
        this.mainActivity?.runWithPermission(
            permission = permission,
            rationaleMessage = rationaleMessage,
            onPermissionGranted = onPermissionGranted,
            onPermissionDenied = onPermissionDenied,
        )
    }

    fun Throwable.toErrorMessage(): String {
        return when (this) {
            is RemoteException -> {
                when (getKind()) {
                    RemoteException.Kind.NETWORK -> {
                        getString(R.string.msg_no_internet_error)
                    }
                    RemoteException.Kind.HTTP -> {
                        getString(R.string.msg_http_error_code, getResponse()?.code())
                    }
                    RemoteException.Kind.HTTP_422_WITH_DATA -> {
                        getErrorData()?.message.defaultEmpty()
                    }
                    RemoteException.Kind.UNEXPECTED -> {
                        getString(R.string.msg_unexpected_error)
                    }
                }
            }
            else -> {
                this.message.defaultEmpty()
            }
        }
    }

    companion object {
        private const val LIFECYCLE_TAG = "FragmentLifecycle"
    }
}