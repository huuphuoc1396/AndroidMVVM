package com.example.androidmvvm.ui.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmvvm.domain.model.error.ApiError
import com.example.androidmvvm.ui.util.livedata.SingleLiveData
import org.koin.core.component.KoinComponent
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), KoinComponent {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val error = SingleLiveData<Error>()
    val tokenExpired = SingleLiveData<Int>()

    fun handleError(error: Error) {
        Timber.e(error)
        when (error) {
            is ApiError.Unauthorized -> {
                tokenExpired.active()
            }
            else -> {
                this.error.value = error
            }
        }
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}