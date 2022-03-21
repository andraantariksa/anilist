package id.shaderboi.anilist.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.databinding.FragmentHomeBinding
import id.shaderboi.anilist.ui.common.adapters.AnimeAdapter
import id.shaderboi.anilist.ui.home.view_models.HomeUIEvent
import id.shaderboi.anilist.ui.home.view_models.HomeViewModel
import id.shaderboi.anilist.ui.search.view_models.SearchUIEvent
import id.shaderboi.anilist.ui.search.view_models.SearchViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//        val navController = findNavController()
//
//        with(binding.toolbar) {
//            setupWithNavController(navController)
//            setOnMenuItemClickListener {
//                when(it.itemId) {
//                    R.id.itemSearch -> {
//
//                        true
//                    }
//                    R.id.itemSort -> {
//
//                        true
//                    }
//                    else -> false
//                }
//            }
//        }

        listenEvent()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun listenEvent() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        homeViewModel.uiEvent.collectLatest { event ->
            when (event) {
                HomeUIEvent.Loading -> {
                    binding.recyclerViewAnimes.visibility = View.GONE
                    binding.linearLayoutSearchAnimation.visibility = View.VISIBLE
                }
//                is SearchUIEvent.Search -> {
//                    binding.recyclerViewAnimes.adapter = AnimeAdapter(event.anime)
//                }
            }
        }
    }
}