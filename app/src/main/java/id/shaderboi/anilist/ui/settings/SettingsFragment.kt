package id.shaderboi.anilist.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.get
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.R
import id.shaderboi.anilist.core.util.Theme
import id.shaderboi.anilist.core.util.preference.AppPreferenceStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    @Inject
    lateinit var appPreferenceStore: AppPreferenceStore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        try {
            val navController = findNavController()
            toolbar.apply {
                navigationIcon =
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_arrow_back_24
                    )
                setNavigationOnClickListener {
                    navController.popBackStack()
                }
            }
        } catch (ex: IllegalStateException) {
            // Ignore
        }

        val themePreference = preferenceScreen.get<ListPreference>("theme")!!
        themePreference.setOnPreferenceChangeListener { preference, newValue ->
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                val newTheme = try {
                    Theme.valueOf(newValue as String)
                } catch (ex: IllegalArgumentException) {
                    Theme.Default
                }
                appPreferenceStore.setTheme(newTheme)
            }

            true
        }

//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            appPreferenceStore.preference().collectLatest { appSettings ->
//                themePreference.value = appSettings.theme.name
//                val themeAppCompat = when(appSettings.theme) {
//                    Theme.Default -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
//                    Theme.Light -> AppCompatDelegate.MODE_NIGHT_NO
//                    Theme.Dark -> AppCompatDelegate.MODE_NIGHT_YES
//                }
//                AppCompatDelegate.setDefaultNightMode(themeAppCompat)
//            }
//        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_app, rootKey)
    }
}