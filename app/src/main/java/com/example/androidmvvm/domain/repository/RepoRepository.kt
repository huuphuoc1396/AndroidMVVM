package com.example.androidmvvm.domain.repository

import com.example.androidmvvm.domain.model.repo.RepoModel
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun searchRepos(query: String): Flow<List<RepoModel>>
}