package com.example.androidmvvm.repository.di

import com.example.androidmvvm.remote.error.RemoteErrorHandler
import com.example.androidmvvm.repository.RepoRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { RemoteErrorHandler() }

    single { RepoRepository(get(), get()) }
}