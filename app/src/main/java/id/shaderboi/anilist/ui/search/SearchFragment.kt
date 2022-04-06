package id.shaderboi.anilist.ui.search

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.R
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.databinding.FragmentSearchBinding
import id.shaderboi.anilist.favorite_anime.ui.FavoriteAnimeFragmentDirections
import id.shaderboi.anilist.ui.common.adapters.AnimePagingAdapter
import id.shaderboi.anilist.ui.common.adapters.animeDataDiffUtil
import id.shaderboi.anilist.ui.search.view_models.SearchEvent
import id.shaderboi.anilist.ui.search.view_models.SearchViewModel
import id.shaderboi.anilist.ui.util.ResourceState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val searchViewModel by viewModels<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    val binding get() = _binding!!

    private lateinit var pagingAdapter: AnimePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupView()
        listenEvent()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setupView() {
        binding.toolbarSearchField.editTextSearch.apply {
            addTextChangedListener { editable ->
                if (isFocused) {
                    searchViewModel.onEvent(SearchEvent.EnqueueSearch(editable.toString()))
                }
            }
        }
        val navController = findNavController()
        pagingAdapter = AnimePagingAdapter(
            { anime, position, view ->
                val action = SearchFragmentDirections
                    .actionNavigationSearchMainToNavigationCommonAnime(anime.malId)
                navController.navigate(action)
            },
            animeDataDiffUtil
        )
        binding.recyclerViewSearchedAnime.apply {
            adapter = pagingAdapter

            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
            dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.divider_vertical_1dp)!!
            )
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun listenEvent() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        launch {
            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                val refreshState = loadStates.refresh
                binding.linearLayoutLoadingAnimation.isVisible = refreshState is LoadState.Loading
                binding.recyclerViewSearchedAnime.isVisible = refreshState !is LoadState.Loading
                binding.includeViewErrorAnimation.root.isVisible = refreshState is LoadState.Error
                if (refreshState is LoadState.Error) {
                    binding.includeViewErrorAnimation.textViewErrorMessage.text = refreshState.error.message
                }
            }
        }

        launch {
            searchViewModel.searchResult.collectLatest { pagingDataFlow ->
                val isIdle = pagingDataFlow == null
                binding.linearLayoutIdleImage.isVisible = isIdle
                binding.linearLayoutLoadingAnimation.isVisible = !isIdle
                binding.recyclerViewSearchedAnime.isVisible = !isIdle
                binding.includeViewErrorAnimation.root.isVisible = !isIdle

                pagingDataFlow?.collectLatest { pagingData ->
                    pagingAdapter.submitData(pagingData)
                }
            }
        }

//        searchViewModel.searchResult.collectLatest { res ->
//            setScreenVisibility(res)
//            if (res is ResourceState.Loaded) {
//                if (res.data.isEmpty()) {
//                    binding.recyclerViewSearchedAnime.visibility = View.GONE
//                    binding.linearLayoutNoResultAnimation.visibility = View.VISIBLE
//                } else {
//                    val navController = binding.root.findNavController()
//                    binding.recyclerViewSearchedAnime.adapter =
//                        AnimePagingAdapter(
//                            res.data,
//                            { anime, position, view ->
//                                val action = SearchFragmentDirections
//                                    .actionNavigationSearchMainToNavigationCommonAnime(anime.malId)
//                                navController.navigate(action)
//                            },
//                            animeDataDiffUtil
//                        )
//                    binding.recyclerViewSearchedAnime.visibility = View.VISIBLE
//                    binding.linearLayoutNoResultAnimation.visibility = View.GONE
//                }
//            }
//        }
//
//        searchViewModel.uiEvent.collectLatest { event ->
//        }
    }
}