package com.example.androidmvvm.data.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidmvvm.data.local.prefs.AppPrefs.PreferencesKeys.KEY_FIRST_RUN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val APP_PREFS_NAME = "app_prefs"
val Context.appPrefs: DataStore<Preferences> by preferencesDataStore(APP_PREFS_NAME)

class AppPrefs constructor(
    private val context: Context
) {

    val isFirstRun: Flow<Boolean?> by lazy { get(KEY_FIRST_RUN) }

    suspend fun setFirstRun(isFirstRun: Boolean) {
        edit(KEY_FIRST_RUN, isFirstRun)
    }

    private fun <T> get(key: Preferences.Key<T>): Flow<T?> =
        context.appPrefs.data.map { prefs -> prefs[key] }

    private suspend fun <T> edit(key: Preferences.Key<T>, value: T) {
        context.appPrefs.edit { prefs -> prefs[key] = value }
    }

    suspend fun clear() {
        context.appPrefs.edit { prefs -> prefs.clear() }
    }

    private object PreferencesKeys {
        val KEY_FIRST_RUN = booleanPreferencesKey("KEY_FIRST_RUN")
    }
}