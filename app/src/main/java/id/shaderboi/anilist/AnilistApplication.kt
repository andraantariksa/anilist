package id.shaderboi.anilist

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import id.shaderboi.anilist.core.util.Theme
import id.shaderboi.anilist.core.util.preference.AppPreferenceStore
import id.shaderboi.anilist.util.log.ReleaseTree
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AnilistApplication : Application() {
    var applicationScope = MainScope()

    @Inject
    lateinit var appPreferenceStore: AppPreferenceStore

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        applicationScope.launch {
            appPreferenceStore.preference().collectLatest { appSettings ->
                val themeAppCompat = when (appSettings.theme) {
                    Theme.Default -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    Theme.Light -> AppCompatDelegate.MODE_NIGHT_NO
                    Theme.Dark -> AppCompatDelegate.MODE_NIGHT_YES
                }
                AppCompatDelegate.setDefaultNightMode(themeAppCompat)
            }
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        applicationScope.cancel()
        applicationScope = MainScope()
    }
}
