package id.shaderboi.anilist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.R
import id.shaderboi.anilist.databinding.FragmentHomeBinding
import id.shaderboi.anilist.ui.common.adapters.AnimePagingAdapter
import id.shaderboi.anilist.ui.common.adapters.animeDataDiffUtil
import id.shaderboi.anilist.ui.home.view_models.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private lateinit var pagingAdapter: AnimePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupView()
        listenEvent()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setupView() {
        val navController = findNavController()
        pagingAdapter = AnimePagingAdapter(
            { anime, position, view ->
                val action = HomeFragmentDirections
                    .actionNavigationHomeMainToNavigationCommonAnime(anime.malId)
                navController.navigate(action)
            },
            animeDataDiffUtil
        )
        binding.recyclerViewAnimes.apply {
            adapter = pagingAdapter

            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
            dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.divider_horizontal_1dp)!!
            )
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun listenEvent() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        launch {
            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                val refreshState = loadStates.refresh
                binding.includeViewLoadingAnimation.root.isVisible = refreshState is LoadState.Loading
                binding.recyclerViewAnimes.isVisible = refreshState !is LoadState.Loading
                binding.includeViewErrorAnimation.root.isVisible = refreshState is LoadState.Error
                if (refreshState is LoadState.Error) {
                    binding.includeViewErrorAnimation.textViewErrorMessage.text = refreshState.error.message
                }
            }
        }

        launch {
            homeViewModel.animeList.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }
}