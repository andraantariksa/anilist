package id.shaderboi.anilist.core.util

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.appSettingsDataStore by preferencesDataStore(AppSettings.NAME)