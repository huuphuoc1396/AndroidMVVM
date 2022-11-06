package com.example.androidmvvm.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidmvvm.local.prefs.AppPrefs.PreferencesKeys.KEY_FIRST_RUN
import com.example.androidmvvm.model.functional.safeSuspendIgnoreFailure
import com.example.androidmvvm.util.extension.defaultTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val APP_PREFS_NAME = "app_data_store"
val Context.appPrefs: DataStore<Preferences> by preferencesDataStore(APP_PREFS_NAME)

class AppPrefs constructor(
    private val context: Context
) {

    suspend fun setFirstRun(isFirstRun: Boolean) = safeSuspendIgnoreFailure {
        edit(KEY_FIRST_RUN, isFirstRun)
    }

    suspend fun isFirstRun(): Boolean = safeSuspendIgnoreFailure {
        get(KEY_FIRST_RUN)
    }.defaultTrue()

    suspend fun clear() {
        context.appPrefs.edit { prefs -> prefs.clear() }
    }

    private suspend fun <T> get(key: Preferences.Key<T>): T? =
        context.appPrefs.data.map { prefs -> prefs[key] }.first()

    private suspend fun <T> edit(key: Preferences.Key<T>, value: T) {
        context.appPrefs.edit { prefs -> prefs[key] = value }
    }

    private object PreferencesKeys {
        val KEY_FIRST_RUN = booleanPreferencesKey("KEY_FIRST_RUN")
    }
}