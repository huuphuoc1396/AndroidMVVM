package com.example.androidmvvm.data.repository.di

import com.example.androidmvvm.data.remote.error.RemoteErrorHandler
import com.example.androidmvvm.data.repository.RepoRepositoryImpl
import com.example.androidmvvm.domain.repository.RepoRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { RemoteErrorHandler() }

    single<RepoRepository> { RepoRepositoryImpl(get(), get()) }
}