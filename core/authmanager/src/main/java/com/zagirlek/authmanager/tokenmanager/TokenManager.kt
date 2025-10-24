package com.zagirlek.authmanager.tokenmanager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenManager(
    private val tokenDataStore: DataStore<Preferences>
) {
    val tokenFlow: Flow<Long?> = tokenDataStore.data .map { prefs -> prefs[TOKEN_KEY] }
    suspend fun saveToken(token: Long) {
        tokenDataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }
    suspend fun clearToken() {
        tokenDataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }
    suspend fun getToken(): Long? {
        val prefs = tokenDataStore.data.first()
        return prefs[TOKEN_KEY]
    }
    companion object {
        private val TOKEN_KEY = longPreferencesKey("auth_token")
    }
}