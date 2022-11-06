package com.example.androidmvvm.local.di

import com.example.androidmvvm.local.prefs.AppPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single { AppPrefs(androidContext()) }
}