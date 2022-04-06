package id.shaderboi.anilist.ui.anime

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.databinding.FragmentAnimeBinding
import id.shaderboi.anilist.databinding.ItemGenreBinding
import id.shaderboi.anilist.ui.anime.view_models.AnimeEvent
import id.shaderboi.anilist.ui.anime.view_models.AnimeViewModel
import id.shaderboi.anilist.ui.util.ResourceState
import id.shaderboi.anilist.ui.util.shown
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
            binding.toolbar.apply {
                navigationIcon =
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_arrow_back_24
                    )
                setNavigationOnClickListener {
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

    private fun setContentVisibility(res: ResourceState<AnimeData, Throwable>) {
        binding.apply {
            when (res) {
                is ResourceState.Error -> {
                    includeViewErrorAnimation.textViewErrorMessage.text = res.error.message

                    linearLayoutContent.visibility = View.GONE
                    includeViewErrorAnimation.root.visibility = View.VISIBLE
                    includeViewLoadingAnimation.root.visibility = View.GONE
                }
                is ResourceState.Loading -> {
                    linearLayoutContent.visibility = View.GONE
                    includeViewErrorAnimation.root.visibility = View.GONE
                    includeViewLoadingAnimation.root.visibility = View.VISIBLE
                }
                is ResourceState.Loaded -> {
                    linearLayoutContent.visibility = View.VISIBLE
                    includeViewErrorAnimation.root.visibility = View.GONE
                    includeViewLoadingAnimation.root.visibility = View.GONE
                }
            }
        }
    }

    private fun shareText(text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun bindTitle(animeData: AnimeData) {
        binding.apply {
            textViewTitle.text = animeData.title

            val isTitleEnglishExists = animeData.titleEnglish != null
            linearLayoutTitleEnglish.shown(isTitleEnglishExists)
            if (isTitleEnglishExists) {
                binding.textViewTitleEnglish.text = animeData.titleEnglish
            }
            val isTitleJapaneseExists = animeData.titleJapanese != null
            linearLayoutTitleJapanese.shown(isTitleJapaneseExists)
            if (isTitleJapaneseExists) {
                binding.textViewTitleJapanese.text = animeData.titleJapanese
            }
            val isTitleAlternativeExists = animeData.titleSynonyms.isNotEmpty()
            linearLayoutTitleAlternative.shown(isTitleAlternativeExists)
            if (isTitleAlternativeExists) {
                textViewTitleAlternative.text = animeData.titleSynonyms.joinToString(", ")
            }
        }
    }

    private fun listenEvent() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        animeViewModel.anime.collectLatest { res ->
            setContentVisibility(res)
            if (res is ResourceState.Loaded) {
                binding.toolbar.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.itemShare -> {
                            shareText(res.data.url)

                            true
                        }
                        else -> false
                    }
                }

                bindTitle(res.data)

                binding.imageViewCover.load(res.data.images.jpg.largeImageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_image_24)
                }

                binding.textViewLicensor.text = res.data.licensors
                    .joinToString(", ") { licensor -> licensor.name }
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

                binding.textViewType.text = res.data.type
                binding.textViewEpisode.text = "${res.data.episodes ?: "?"} ep"
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

                animeViewModel.isFavoriteAnime.collectLatest { isFavorite ->
                    val favoriteDrawable = if (isFavorite) {
                        R.drawable.ic_baseline_favorite_24
                    } else {
                        R.drawable.ic_baseline_favorite_border_24
                    }
                    binding.floatingActionButtonFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            favoriteDrawable
                        )
                    )

                    binding.floatingActionButtonFavorite.setOnClickListener {
                        animeViewModel.onEvent(AnimeEvent.ToggleAnimeFavorite(args.malId))
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