package com.example.androidmvvm.ui.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmvvm.ui.util.livedata.SingleLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import org.koin.core.component.KoinComponent

abstract class BaseViewModel : ViewModel(), KoinComponent {
    val error = SingleLiveData<Throwable>()
    val loading = MutableLiveData<Boolean>()

    fun <T> Flow<T>.handleError() = catch { throwable -> error.value = throwable }

    fun setLoading(isLoading: Boolean) {
        loading.value = isLoading
    }
}