package com.example.androidmvvm.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.androidmvvm.R
import com.example.androidmvvm.databinding.ActivityMainBinding
import com.example.androidmvvm.feature.dialog.MessageDialogFragment
import com.example.androidmvvm.feature.main.MainFragment
import com.example.androidmvvm.util.extension.*
import com.tunjid.androidx.navigation.stackNavigationController
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LIFECYCLE_TAG = "ActivityLifecycle"
    }

    private var onPermissionGranted: (() -> Unit)? = null
    private var onPermissionDenied: (() -> Unit)? = null
    private val requestPermissionLauncher = registerPermissionForResult(
        onPermissionGranted = {
            onPermissionGranted?.invoke()
        },
        onPermissionDenied = {
            onPermissionDenied?.invoke()
        },
    )

    private fun startDestination(): Fragment = MainFragment.newInstance()

    var viewBinding: ActivityMainBinding? = null
    val stackNavigator by stackNavigationController(R.id.navHost)

    override fun onRestart() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onRestart")
        super.onRestart()
    }

    override fun onStart() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onStart")
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onCreate")
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding!!.root)

        if (savedInstanceState == null) {
            val rootFragment = startDestination()
            stackNavigator.push(rootFragment)
            stackNavigator.transactionModifier = { slideFade() }
        }
    }

    fun runWithPermission(
        permission: String,
        rationaleMessage: String,
        onPermissionGranted: () -> Unit = {},
        onPermissionDenied: () -> Unit = {}
    ) {
        this.onPermissionGranted = onPermissionGranted
        this.onPermissionDenied = onPermissionDenied
        if (hasPermission(permission)) {
            onPermissionGranted()
        } else {
            requestPermission(permission, rationaleMessage)
        }
    }

    private fun requestPermission(permission: String, rationaleMessage: String) {
        if (shouldShowRequestPermissionRationaleCompat(permission)) {
            MessageDialogFragment.newInstance(
                message = rationaleMessage
            ).setPositiveButton(
                title = getString(R.string.txt_Ok),
                onClickListener = {
                    requestPermissionLauncher.launch(permission)
                },
            ).setNegativeButton(
                title = getString(R.string.txt_close),
                onClickListener = {
                    onPermissionDenied?.invoke()
                },
            ).showIfNotExist(
                fragmentManager = supportFragmentManager,
                tag = MessageDialogFragment.TAG,
            )
        } else {
            requestPermissionLauncher.launch(permission)
        }
    }

    private fun clearAllPermissionCallback() {
        onPermissionGranted = null
        onPermissionDenied = null
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

    override fun onDestroy() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onDestroy")
        viewBinding = null
        clearAllPermissionCallback()
        super.onDestroy()
    }

}