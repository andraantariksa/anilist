package id.shaderboi.anilist.core.util.preference

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import id.shaderboi.anilist.core.util.AppSettings
import id.shaderboi.anilist.core.util.Theme
import id.shaderboi.anilist.core.util.appSettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AppPreferenceStoreImpl @Inject constructor(private val context: Context) :
    AppPreferenceStore {
    override fun preference(): Flow<AppSettings> = context.appSettingsDataStore
        .data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e("Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            mapAppPreferences(preferences)
        }

    private fun mapAppPreferences(preferences: Preferences): AppSettings {
        val theme = Theme.valueOf(preferences[AppSettings.Key.THEME] ?: Theme.Default.name)

        return AppSettings(theme)
    }

    override suspend fun setTheme(theme: Theme) {
        context.appSettingsDataStore.edit { preferences ->
            preferences[AppSettings.Key.THEME] = theme.name
        }
    }
}