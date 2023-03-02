package com.example.androidmvvm.domain.model.repo

data class RepoModel(
    val id: Long,
    val repoName: String,
    val ownerName: String,
    val imageUrl: String,
)