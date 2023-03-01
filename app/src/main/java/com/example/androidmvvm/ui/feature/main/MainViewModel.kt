package com.example.androidmvvm.ui.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidmvvm.data.local.prefs.AppPrefs
import com.example.androidmvvm.domain.model.functional.onError
import com.example.androidmvvm.domain.model.functional.onSuccess
import com.example.androidmvvm.domain.model.main.RepoModel
import com.example.androidmvvm.domain.repository.RepoRepository
import com.example.androidmvvm.ui.platform.BaseViewModel
import com.example.androidmvvm.ui.util.livedata.SingleLiveData
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val repoRepository: RepoRepository,
    private val appPrefs: AppPrefs,
) : BaseViewModel() {

    val repoList = MutableLiveData<List<RepoModel>>()
    val isFirstRun = SingleLiveData<Boolean>()

    init {
        checkFirstRun()
        searchRepos("Android")
    }

    fun searchRepos(query: String, isRefreshing: Boolean = false) {
        viewModelScope.launch {
            setLoading(!isRefreshing)
            repoRepository.searchRepos(query)
                .onError(::handleError)
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