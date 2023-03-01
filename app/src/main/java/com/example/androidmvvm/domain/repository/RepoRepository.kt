package com.example.androidmvvm.domain.repository

import com.example.androidmvvm.domain.model.functional.ResultWrapper
import com.example.androidmvvm.domain.model.main.RepoModel

interface RepoRepository {
    suspend fun searchRepos(query: String): ResultWrapper<List<RepoModel>>
}