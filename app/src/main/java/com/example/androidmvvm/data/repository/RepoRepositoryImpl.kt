package com.example.androidmvvm.data.repository

import com.example.androidmvvm.data.remote.response.search_repo.toRepoItem
import com.example.androidmvvm.data.remote.retrofit.api.ApiService
import com.example.androidmvvm.domain.model.repo.RepoModel
import com.example.androidmvvm.domain.repository.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RepoRepositoryImpl constructor(
    private val apiService: ApiService,
) : RepoRepository {

    override suspend fun searchRepos(query: String): Flow<List<RepoModel>> {
        return apiService.searchRepos(query).map { searchRepoResponse ->
            searchRepoResponse.items?.map { item -> item.toRepoItem() }.orEmpty()
        }.flowOn(Dispatchers.IO)
    }
}