package com.example.androidmvvm.data.repository.di

import com.example.androidmvvm.data.repository.RepoRepositoryImpl
import com.example.androidmvvm.domain.repository.RepoRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<RepoRepository> { RepoRepositoryImpl(get()) }
}