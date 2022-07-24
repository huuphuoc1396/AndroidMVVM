package com.example.androidmvvm.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidmvvm.core.functional.onError
import com.example.androidmvvm.core.functional.onSuccess
import com.example.androidmvvm.core.livedata.SingleLiveData
import com.example.androidmvvm.core.platform.BaseViewModel
import com.example.androidmvvm.feature.main.model.RepoItem
import com.example.androidmvvm.local.prefs.AppPrefs
import com.example.androidmvvm.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
    private val appPrefs: AppPrefs,
) : BaseViewModel() {

    val repoList = MutableLiveData<List<RepoItem>>()
    val isFirstRun = SingleLiveData<Boolean>()

    init {
        checkFirstRun()
        searchRepos("Android")
    }

    fun searchRepos(query: String, isRefreshing: Boolean = false) {
        viewModelScope.launch {
            setLoading(!isRefreshing)
            repoRepository.searchRepos(query)
                .onError(::handleFailure)
                .onSuccess {
                    repoList.value = it
                }
            setLoading(false)
        }
    }

    private fun checkFirstRun() {
        viewModelScope.launch {
            isFirstRun.value = appPrefs.isFirstRun()
            appPrefs.setFirstRun(false)
        }
    }
}