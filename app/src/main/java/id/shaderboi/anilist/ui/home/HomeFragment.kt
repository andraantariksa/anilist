package id.shaderboi.anilist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.R
import id.shaderboi.anilist.databinding.FragmentHomeBinding
import id.shaderboi.anilist.ui.common.adapters.AnimeAdapter
import id.shaderboi.anilist.ui.home.view_models.HomeViewModel
import id.shaderboi.anilist.ui.util.ResourceState
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

        binding.recyclerViewAnimes.apply {
            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
            dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.divider_vertical_1dp)!!
            )
            addItemDecoration(dividerItemDecoration)
        }

        listenEvent()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun listenEvent() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        homeViewModel.animeList.collectLatest { res ->
            when (res) {
                is ResourceState.Error -> {
                    binding.textViewErrorMessage.text = res.error.message
                    binding.recyclerViewAnimes.visibility = View.GONE
                    binding.linearLayoutErrorAnimation.visibility = View.VISIBLE
                    binding.linearLayoutLoadingAnimation.visibility = View.GONE
                }
                is ResourceState.Loading -> {
                    binding.recyclerViewAnimes.visibility = View.GONE
                    binding.linearLayoutErrorAnimation.visibility = View.GONE
                    binding.linearLayoutLoadingAnimation.visibility = View.VISIBLE
                }
                is ResourceState.Loaded -> {
                    val navController = binding.root.findNavController()

                    binding.recyclerViewAnimes.adapter =
                        AnimeAdapter(res.data, navController) { anime, position, view ->
                            val action = HomeFragmentDirections
                                .actionNavigationHomeMainToNavigationCommonAnime(anime.malId)
                            navController.navigate(action)
                        }

                    binding.recyclerViewAnimes.visibility = View.VISIBLE
                    binding.linearLayoutErrorAnimation.visibility = View.GONE
                    binding.linearLayoutLoadingAnimation.visibility = View.GONE
                }
            }
        }

        homeViewModel.uiEvent.collectLatest { event ->
            when (event) {
            }
        }
    }
}