package com.example.androidmvvm.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidmvvm.core.extension.defaultTrue
import com.example.androidmvvm.core.functional.safeSuspendIgnoreFailure
import com.example.androidmvvm.local.prefs.AppPrefs.PreferencesKeys.KEY_FIRST_RUN
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val APP_PREFS_NAME = "app_data_store"
val Context.appPrefs: DataStore<Preferences> by preferencesDataStore(APP_PREFS_NAME)

class AppPrefs @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    suspend fun setFirstRun(isFirstRun: Boolean) = safeSuspendIgnoreFailure {
        edit { it[KEY_FIRST_RUN] = isFirstRun }
    }

    suspend fun isFirstRun(): Boolean = safeSuspendIgnoreFailure {
        get { it[KEY_FIRST_RUN] }
    }.defaultTrue()

    private suspend fun <T> get(transform: (Preferences) -> T): T =
        context.appPrefs.data.map { transform(it) }.first()

    private suspend fun edit(transform: suspend (MutablePreferences) -> Unit) {
        context.appPrefs.edit(transform)
    }

    private object PreferencesKeys {
        val KEY_FIRST_RUN = booleanPreferencesKey("KEY_FIRST_RUN")
    }
}