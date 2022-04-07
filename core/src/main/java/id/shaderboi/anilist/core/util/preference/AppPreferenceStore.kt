package id.shaderboi.anilist.core.util.preference

import id.shaderboi.anilist.core.util.AppSettings
import id.shaderboi.anilist.core.util.Theme
import kotlinx.coroutines.flow.Flow

interface AppPreferenceStore {
    suspend fun setTheme(theme: Theme)
    fun preference(): Flow<AppSettings>
}