package id.shaderboi.anilist.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import java.io.InputStream
import java.io.OutputStream

@JsonClass(generateAdapter = true)
enum class Theme {
    Default,
    Light,
    Dark
}

@JsonClass(generateAdapter = true)
data class AppSettings(
    val theme: Theme = Theme.Default
)

object SettingsSerializer : Serializer<AppSettings> {
    private val jsonAdapter = Moshi.Builder().build().adapter(AppSettings::class.java)

    override val defaultValue: AppSettings = AppSettings()

    override suspend fun readFrom(input: InputStream): AppSettings {
        // Unsafe
        return jsonAdapter.fromJson(input.readBytes().decodeToString())!!
    }

    override suspend fun writeTo(t: AppSettings, output: OutputStream) {
        output.write(jsonAdapter.toJson(t).toByteArray())
    }
}

val Context.settingsDataStore: DataStore<AppSettings> by dataStore(
    fileName = "app_settings.json",
    serializer = SettingsSerializer
)