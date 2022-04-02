package id.shaderboi.anilist.ui.misc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.R
import id.shaderboi.anilist.databinding.FragmentMiscBinding
import id.shaderboi.anilist.databinding.ItemPreferenceBinding
import id.shaderboi.anilist.ui.search.view_models.SearchUIEvent
import id.shaderboi.anilist.ui.search.view_models.SearchViewModel
import kotlinx.coroutines.flow.collectLatest

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

        val settingsItemContainer = ItemPreferenceBinding.inflate(inflater)
        settingsItemContainer.title.text = "Settings"
        settingsItemContainer.icon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_settings_24))

        binding.linearLayoutPreference.addView(settingsItemContainer.root)

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