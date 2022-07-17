package com.example.androidmvvm.repository

import com.example.androidmvvm.core.functional.ResultWrapper
import com.example.androidmvvm.core.functional.safeSuspend
import com.example.androidmvvm.feature.main.model.RepoItem
import com.example.androidmvvm.remote.error.RemoteFailureHandler
import com.example.androidmvvm.remote.response.search_repo.toRepoItem
import com.example.androidmvvm.remote.retrofit.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val apiService: ApiService,
    private val remoteFailureHandler: RemoteFailureHandler,
) {

    suspend fun searchRepos(query: String): ResultWrapper<List<RepoItem>> {
        return safeSuspend(remoteFailureHandler) {
            val searchRepoResponse = apiService.searchRepos(query)
            val repoList = searchRepoResponse.items?.map { item ->
                item.toRepoItem()
            }.orEmpty()
            ResultWrapper.Success(repoList)
        }
    }
}