package com.zagirlek.android.tokenmanager

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCES = "token_prefs"

val Context.tokenDataStore by preferencesDataStore(name = USER_PREFERENCES)