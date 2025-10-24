package com.zagirlek.authmanager.tokenmanager

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCES = "token_prefs"

internal val Context.tokenDataStore by preferencesDataStore(name = USER_PREFERENCES)