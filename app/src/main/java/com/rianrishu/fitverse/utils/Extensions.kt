package com.rianrishu.fitverse.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore("FitVerseDataStore")