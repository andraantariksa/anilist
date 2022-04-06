package id.shaderboi.anilist.favorite_anime.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.EntryPointAccessors
import id.shaderboi.anilist.R
import id.shaderboi.anilist.core.di.FavoriteAnimeDynamicFeatureDependencies
import id.shaderboi.anilist.favorite.databinding.FragmentFavoriteAnimeBinding
import id.shaderboi.anilist.favorite_anime.di.DaggerFavoriteAnimeComponent
import id.shaderboi.anilist.favorite_anime.ui.view_models.FavoriteAnimeViewModel
import id.shaderboi.anilist.favorite_anime.ui.view_models.FavoriteAnimeViewModelFactory
import id.shaderboi.anilist.ui.common.adapters.AnimePagingAdapter
import id.shaderboi.anilist.ui.common.adapters.animeDataDiffUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteAnimeFragment : Fragment() {
    @Inject
    lateinit var favoriteAnimeViewModelFactory: FavoriteAnimeViewModelFactory
    private val favoriteAnimeViewModel: FavoriteAnimeViewModel by viewModels {
        favoriteAnimeViewModelFactory
    }

    private var _binding: FragmentFavoriteAnimeBinding? = null
    val binding get() = _binding!!

    private lateinit var pagingAdapter: AnimePagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteAnimeComponent
            .factory()
            .create(
                EntryPointAccessors.fromApplication(
                    requireContext().applicationContext,
                    FavoriteAnimeDynamicFeatureDependencies::class.java
                )
            )
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteAnimeBinding.inflate(inflater, container, false)

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)

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
                val action = FavoriteAnimeFragmentDirections
                    .actionNavigationFavoriteMainToNavigationCommonAnime(anime.malId)
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
                ContextCompat.getDrawable(requireContext(), R.drawable.divider_vertical_1dp)!!
            )
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun listenEvent() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        launch {
            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                val refreshState = loadStates.refresh
                binding.recyclerViewAnimes.isVisible = refreshState !is LoadState.Loading
                binding.linearLayoutLoadingAnimation.isVisible = refreshState is LoadState.Loading
                binding.linearLayoutErrorAnimation.isVisible = refreshState is LoadState.Error
                if (refreshState is LoadState.Error) {
                    binding.textViewErrorMessage.text = refreshState.error.message
                }
            }
        }

        launch {
            favoriteAnimeViewModel.favoriteAnimeList.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

//        homeViewModel.uiEvent.collectLatest { event ->
//            when (event) {
//            }
//        }
    }
}