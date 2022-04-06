package id.shaderboi.anilist.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import id.shaderboi.anilist.R
import java.lang.IllegalStateException

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

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

        return view
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey)
    }
}