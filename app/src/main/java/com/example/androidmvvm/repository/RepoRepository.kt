package com.example.androidmvvm.repository

import com.example.androidmvvm.model.functional.ResultWrapper
import com.example.androidmvvm.model.functional.safeSuspend
import com.example.androidmvvm.model.main.RepoModel
import com.example.androidmvvm.remote.error.RemoteErrorHandler
import com.example.androidmvvm.remote.response.search_repo.toRepoItem
import com.example.androidmvvm.remote.retrofit.api.ApiService

class RepoRepository constructor(
    private val apiService: ApiService,
    private val remoteFailureHandler: RemoteErrorHandler,
) {

    suspend fun searchRepos(query: String): ResultWrapper<List<RepoModel>> {
        return safeSuspend(remoteFailureHandler) {
            val searchRepoResponse = apiService.searchRepos(query)
            val repoList = searchRepoResponse.items?.map { item ->
                item.toRepoItem()
            }.orEmpty()
            ResultWrapper.Success(repoList)
        }
    }
}