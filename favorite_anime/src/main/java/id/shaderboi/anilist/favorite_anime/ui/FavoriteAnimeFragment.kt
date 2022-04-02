package id.shaderboi.anilist.favorite_anime.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.EntryPointAccessors
import id.shaderboi.anilist.core.di.FavoriteAnimeDynamicFeatureDependencies
import id.shaderboi.anilist.favorite.databinding.FragmentFavoriteBinding
import id.shaderboi.anilist.favorite_anime.di.DaggerFavoriteAnimeComponent
import id.shaderboi.anilist.favorite_anime.ui.view_models.FavoriteAnimeViewModel
import id.shaderboi.anilist.favorite_anime.ui.view_models.FavoriteAnimeViewModelFactory
import id.shaderboi.anilist.ui.common.adapters.AnimeAdapter
import id.shaderboi.anilist.ui.util.ResourceState
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class FavoriteAnimeFragment : Fragment() {
    @Inject
    lateinit var favoriteAnimeViewModelFactory: FavoriteAnimeViewModelFactory
    private val favoriteAnimeViewModel: FavoriteAnimeViewModel by viewModels {
        favoriteAnimeViewModelFactory
    }

    //    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private var _binding: FragmentFavoriteBinding? = null
    val binding get() = _binding!!

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
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        listenEvent()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun listenEvent() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        favoriteAnimeViewModel.favoriteAnimeList.collectLatest { res ->
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
                        AnimeAdapter(
                            res.data.filter { it.anime != null }.map { it.anime!!.anime },
                            navController
                        ) { anime, position, view ->
                            val action = FavoriteAnimeFragmentDirections
                                .actionNavigationFavoriteMainToNavigationCommonAnime(anime.malId)
                            navController.navigate(action)
                        }

                    binding.recyclerViewAnimes.visibility = View.VISIBLE
                    binding.linearLayoutErrorAnimation.visibility = View.GONE
                    binding.linearLayoutLoadingAnimation.visibility = View.GONE
                }
            }
        }

//        homeViewModel.uiEvent.collectLatest { event ->
//            when (event) {
//            }
//        }
    }
}