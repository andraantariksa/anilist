package id.shaderboi.anilist.ui.misc

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.BuildConfig
import id.shaderboi.anilist.R
import id.shaderboi.anilist.databinding.FragmentMiscBinding
import id.shaderboi.anilist.databinding.ItemPreferenceBinding

@AndroidEntryPoint
class MiscFragment : Fragment() {
    //    private val homeViewModel by viewModels<SearchViewModel>()
    private var _binding: FragmentMiscBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMiscBinding.inflate(inflater, container, false)

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val settingsItemContainer =
            ItemPreferenceBinding.inflate(inflater, binding.linearLayoutPreference, false)
        settingsItemContainer.title.text = "Settings"
        settingsItemContainer.imageFrame.icon.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_settings_24
            )
        )
        val navController = findNavController()
        settingsItemContainer.root.setOnClickListener {
            val action = MiscFragmentDirections.actionNavigationMiscMainToNavigationMiscSettings()
            navController.navigate(action)
        }

        binding.linearLayoutPreference.addView(settingsItemContainer.root)

        val versionItemContainer =
            ItemPreferenceBinding.inflate(inflater, binding.linearLayoutPreference, false)
        versionItemContainer.title.text = "Version"
        versionItemContainer.summary.text = BuildConfig.VERSION_NAME
        versionItemContainer.summary.visibility = View.VISIBLE
        versionItemContainer.imageFrame.icon.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_info_24
            )
        )

        binding.linearLayoutPreference.addView(versionItemContainer.root)

        listenEvent()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun listenEvent() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
//        homeViewModel.uiEvent.collectLatest { event ->
//            when (event) {
//                SearchUIEvent.AnimeSearchLoadError -> TODO()
//                is SearchUIEvent.AnimeSearchLoaded -> TODO()
//            }
//        }
    }
}