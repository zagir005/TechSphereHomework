package com.zagirlek.android.tokenmanager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first

class AuthTokenManager(
    private val tokenDataStore: DataStore<Preferences>
) {
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