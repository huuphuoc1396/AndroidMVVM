package com.example.androidmvvm

import android.app.Application
import com.example.androidmvvm.feature.di.viewModelModule
import com.example.androidmvvm.local.di.localModule
import com.example.androidmvvm.remote.di.remoteModule
import com.example.androidmvvm.repository.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(applicationContext)
            modules(
                viewModelModule,
                repositoryModule,
                localModule,
                remoteModule,
            )
        }
    }
}