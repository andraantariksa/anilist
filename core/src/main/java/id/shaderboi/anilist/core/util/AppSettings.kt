package id.shaderboi.anilist.core.util

import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.JsonClass

data class AppSettings(
    val theme: Theme = Theme.Default
) {
    companion object {
        const val NAME = "app_settings"
    }

    object Name {
        const val THEME = "theme"
    }

    object Key {
        val THEME = stringPreferencesKey(Name.THEME)
    }
}