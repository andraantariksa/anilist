package id.shaderboi.anilist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import id.shaderboi.anilist.util.log.ReleaseTree
import timber.log.Timber

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
