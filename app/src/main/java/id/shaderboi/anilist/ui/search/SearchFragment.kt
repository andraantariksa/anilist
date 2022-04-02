package id.shaderboi.anilist.ui.search

import android.os.Bundle
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.databinding.FragmentSearchBinding
import id.shaderboi.anilist.ui.common.adapters.AnimeAdapter
import id.shaderboi.anilist.ui.home.HomeFragmentDirections
import id.shaderboi.anilist.ui.search.view_models.SearchEvent
import id.shaderboi.anilist.ui.search.view_models.SearchUIEvent
import id.shaderboi.anilist.ui.search.view_models.SearchViewModel
import id.shaderboi.anilist.ui.util.ResourceState
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val searchViewModel by viewModels<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        with(binding.toolbarSearchField.editTextSearch) {
            requestFocus()
            addTextChangedListener { editable ->
                searchViewModel.onEvent(SearchEvent.EnqueueSearch(editable.toString()))
            }
        }
        listenEvent()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun listenEvent() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        searchViewModel.searchResult.collectLatest { event ->
            when (event) {
                null -> {
                    binding.frameLayoutLoaded.visibility = View.GONE
                    binding.linearLayoutLoadingAnimation.visibility = View.GONE
                    binding.linearLayoutErrorAnimation.visibility = View.GONE
                    binding.linearLayoutIdleImage.visibility = View.VISIBLE
                }
                is ResourceState.Loading -> {
                    binding.frameLayoutLoaded.visibility = View.GONE
                    binding.linearLayoutLoadingAnimation.visibility = View.VISIBLE
                    binding.linearLayoutErrorAnimation.visibility = View.GONE
                    binding.linearLayoutIdleImage.visibility = View.GONE
                }
                is ResourceState.Error -> {
                    binding.frameLayoutLoaded.visibility = View.GONE
                    binding.linearLayoutLoadingAnimation.visibility = View.GONE
                    binding.linearLayoutErrorAnimation.visibility = View.VISIBLE
                    binding.linearLayoutIdleImage.visibility = View.GONE
                }
                is ResourceState.Loaded -> {
                    if (event.data.isEmpty()) {
                        binding.recyclerViewSearchedAnime.visibility = View.GONE
                        binding.linearLayoutNoResultAnimation.visibility = View.VISIBLE
                    } else {
                        val navController = binding.root.findNavController()
                        binding.recyclerViewSearchedAnime.adapter =
                            AnimeAdapter(event.data, navController)  { anime, position, view ->
                                val action = SearchFragmentDirections
                                    .actionNavigationSearchMainToNavigationCommonAnime(anime.malId)
                                navController.navigate(action)
                            }
                        binding.recyclerViewSearchedAnime.visibility = View.VISIBLE
                        binding.linearLayoutNoResultAnimation.visibility = View.GONE
                    }

                    binding.frameLayoutLoaded.visibility = View.VISIBLE
                    binding.linearLayoutLoadingAnimation.visibility = View.GONE
                    binding.linearLayoutErrorAnimation.visibility = View.GONE
                    binding.linearLayoutIdleImage.visibility = View.GONE
                }
            }
        }

        searchViewModel.uiEvent.collectLatest { event ->
        }
    }
}