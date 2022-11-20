package com.movingroot.storyfromnowhere.util

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import okio.IOException

class DataStoreModule(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "settings")

    suspend fun storeValue(key: String, value: Any) {
        context.dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
            }
        }
    }

    suspend fun retrieveString(key: String): Flow<String> {
        return makeFlowPreferences()
            .map { preferences ->
                preferences[stringPreferencesKey(key)] ?: ""
            }
    }

    suspend fun retrieveBoolean(key: String): Flow<Boolean> {
        return makeFlowPreferences()
            .map { preferences ->
                preferences[booleanPreferencesKey(key)] ?: false
            }
    }

    suspend fun retrieveInt(key: String): Flow<Int> {
        return makeFlowPreferences()
            .map { preferences ->
                preferences[intPreferencesKey(key)] ?: 0
            }
    }

    private suspend fun makeFlowPreferences(): Flow<Preferences> {
        return context.dataStore.data
            .catch { e ->
                if (e is IOException)
                    emit(emptyPreferences())
                else
                    throw e
            }
    }
}
