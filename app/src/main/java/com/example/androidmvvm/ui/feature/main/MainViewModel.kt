package com.example.androidmvvm.ui.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidmvvm.data.local.prefs.AppPrefs
import com.example.androidmvvm.domain.model.repo.RepoModel
import com.example.androidmvvm.domain.repository.RepoRepository
import com.example.androidmvvm.ui.platform.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val repoRepository: RepoRepository,
    private val appPrefs: AppPrefs,
) : BaseViewModel() {

    val repoList = MutableLiveData<List<RepoModel>>()
    val firstRun = appPrefs.isFirstRun.asLiveData()

    init {
        searchRepos("Android")
        setFirstRun()
    }

    fun searchRepos(query: String, isRefreshing: Boolean = false) {
        viewModelScope.launch {
            setLoading(!isRefreshing)
            repoRepository.searchRepos(query)
                .handleError()
                .collectLatest { list ->
                    repoList.value = list
                }
        }.invokeOnCompletion {
            setLoading(false)
        }
    }

    private fun setFirstRun() {
        viewModelScope.launch {
            appPrefs.setFirstRun(false)
        }
    }
}