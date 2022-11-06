package com.example.androidmvvm.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmvvm.util.livedata.SingleLiveData

abstract class BaseViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val error = SingleLiveData<Error>()

    fun handleFailure(failure: Error) {
        this.error.value = failure
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}