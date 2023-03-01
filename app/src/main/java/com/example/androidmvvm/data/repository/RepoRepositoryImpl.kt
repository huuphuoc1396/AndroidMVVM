package com.example.androidmvvm.data.repository

import com.example.androidmvvm.data.remote.error.RemoteErrorHandler
import com.example.androidmvvm.data.remote.response.search_repo.toRepoItem
import com.example.androidmvvm.data.remote.retrofit.api.ApiService
import com.example.androidmvvm.domain.model.functional.ResultWrapper
import com.example.androidmvvm.domain.model.functional.safeSuspend
import com.example.androidmvvm.domain.model.main.RepoModel
import com.example.androidmvvm.domain.repository.RepoRepository

class RepoRepositoryImpl constructor(
    private val apiService: ApiService,
    private val remoteFailureHandler: RemoteErrorHandler,
) : RepoRepository {

    override suspend fun searchRepos(query: String): ResultWrapper<List<RepoModel>> {
        return safeSuspend(remoteFailureHandler) {
            val searchRepoResponse = apiService.searchRepos(query)
            val repoList = searchRepoResponse.items?.map { item ->
                item.toRepoItem()
            }.orEmpty()
            ResultWrapper.Success(repoList)
        }
    }
}