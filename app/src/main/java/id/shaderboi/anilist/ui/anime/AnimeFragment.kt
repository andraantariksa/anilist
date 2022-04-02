package id.shaderboi.anilist.ui.anime

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.R
import id.shaderboi.anilist.databinding.FragmentAnimeBinding
import id.shaderboi.anilist.databinding.ItemGenreBinding
import id.shaderboi.anilist.ui.anime.view_models.AnimeEvent
import id.shaderboi.anilist.ui.anime.view_models.AnimeViewModel
import id.shaderboi.anilist.ui.util.ResourceState
import kotlinx.coroutines.flow.collectLatest
import java.lang.IllegalStateException

@AndroidEntryPoint
class AnimeFragment : Fragment() {
    private val animeViewModel by viewModels<AnimeViewModel>()
    private var _binding: FragmentAnimeBinding? = null
    val binding get() = _binding!!

    private val args: AnimeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animeViewModel.onEvent(AnimeEvent.Load(args.malId))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeBinding.inflate(inflater, container, false)

        try {
            val navController = findNavController()
            if (navController.backQueue.size > 0) {
                binding.toolbar.navigationIcon =
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_arrow_back_24
                    )
                binding.toolbar.setNavigationOnClickListener {
                    navController.popBackStack()
                }
            }
        } catch (ex: IllegalStateException) {
            // Ignore
        }
        listenEvent()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun listenEvent() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        animeViewModel.anime.collectLatest { res ->
            when (res) {
                is ResourceState.Error -> {
                    binding.includeViewErrorAnimation.textViewErrorMessage.text = res.error.message

                    binding.linearLayoutContent.visibility = View.GONE
                    binding.includeViewErrorAnimation.root.visibility = View.VISIBLE
                    binding.includeViewLoadingAnimation.root.visibility = View.GONE
                }
                is ResourceState.Loading -> {
                    binding.linearLayoutContent.visibility = View.GONE
                    binding.includeViewErrorAnimation.root.visibility = View.GONE
                    binding.includeViewLoadingAnimation.root.visibility = View.VISIBLE
                }
                is ResourceState.Loaded -> {
                    binding.toolbar.title = res.data.title

                    binding.imageViewCover.load(res.data.images.jpg.largeImageUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_baseline_image_24)
                    }

                    binding.textViewLicensor.text = res.data.licensors
                        .joinToString(", ")
                        .ifBlank { "Not available" }
                    binding.textViewAired.text = res.data.aired.string ?: "Not available"
                    binding.textViewDuration.text = res.data.duration
                    binding.textViewRating.text = res.data.rating
                    binding.textViewSeason.text = res.data.season ?: "Not available"
                    binding.textViewSource.text = res.data.source

                    binding.flexboxLayoutGenres.removeAllViews()
                    res.data.genres.forEach { genre ->
                        val genreView = ItemGenreBinding.inflate(layoutInflater)
                        genreView.textViewGenre.text = genre.name
                        binding.flexboxLayoutGenres.addView(genreView.root)
                    }

                    binding.textViewEpisode.text = "${res.data.episodes ?: "?"} ep"
                    binding.textViewType.text = res.data.type
                    binding.textViewAiringStatus.text = res.data.status

                    binding.textViewSynopsis.text = res.data.synopsis ?: "Not available"

                    res.data.trailer?.youtubeId?.let { youtubeVideoId ->
                        binding.linearLayoutYoutube.visibility = View.VISIBLE
                        binding.youtubePlayerView.initialize(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.cueVideo(youtubeVideoId, 0.0f)
                            }
                        })
                    }

                    binding.linearLayoutContent.visibility = View.VISIBLE
                    binding.includeViewErrorAnimation.root.visibility = View.GONE
                    binding.includeViewLoadingAnimation.root.visibility = View.GONE

                    animeViewModel.isFavoriteAnime.collectLatest { isFavorite ->
                        val color = ContextCompat.getColor(
                            requireContext(),
                            if (isFavorite) R.color.purple_500 else R.color.black
                        )
                        binding.floatingActionButtonFavorite.backgroundTintList =
                            ColorStateList.valueOf(color)

                        binding.floatingActionButtonFavorite.setOnClickListener {
                            animeViewModel.onEvent(AnimeEvent.ToggleAnimeFavorite(args.malId))
                        }
                    }
                }
            }
        }

//        animeViewModel.uiEvent.collectLatest { event ->
//            when (event) {
//            }
//        }
    }
}