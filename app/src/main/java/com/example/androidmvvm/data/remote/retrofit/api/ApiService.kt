package com.example.androidmvvm.data.remote.retrofit.api

import com.example.androidmvvm.data.remote.response.search_repo.SearchRepoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/search/repositories")
    suspend fun searchRepos(@Query("q") query: String): SearchRepoResponse
}