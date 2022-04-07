package id.shaderboi.anilist

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import id.shaderboi.anilist.core.util.Theme
import id.shaderboi.anilist.core.util.appSettingsDataStore
import id.shaderboi.anilist.core.util.preference.AppPreferenceStore
import id.shaderboi.anilist.util.log.ReleaseTree
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AnilistApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}
