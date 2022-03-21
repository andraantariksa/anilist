package id.shaderboi.anilist.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import id.shaderboi.anilist.databinding.FragmentAnimeBinding
import id.shaderboi.anilist.ui.anime.view_models.AnimeEvent
import id.shaderboi.anilist.ui.anime.view_models.AnimeViewModel
import id.shaderboi.anilist.ui.util.ResourceState
import kotlinx.coroutines.flow.collectLatest

class AnimeFragment : Fragment() {
    private val animeViewModel by viewModels<AnimeViewModel>()
    private var _binding: FragmentAnimeBinding? = null
    val binding get() = _binding!!

    val args: AnimeFragmentArgs by navArgs()

    init {
        animeViewModel.onEvent(AnimeEvent.Load(args.id))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeBinding.inflate(inflater, container, false)

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
                    binding.textViewErrorMessage.text = res.error.message
                    binding.linearLayoutContent.visibility = View.GONE
                    binding.linearLayoutErrorAnimation.visibility = View.VISIBLE
                    binding.linearLayoutLoadingAnimation.visibility = View.GONE
                }
                is ResourceState.Loading -> {
                    binding.linearLayoutContent.visibility = View.GONE
                    binding.linearLayoutErrorAnimation.visibility = View.GONE
                    binding.linearLayoutLoadingAnimation.visibility = View.VISIBLE
                }
                is ResourceState.Loaded -> {
                    binding.toolbar.title = res.data.data.title

                    binding.linearLayoutContent.visibility = View.VISIBLE
                    binding.linearLayoutErrorAnimation.visibility = View.GONE
                    binding.linearLayoutLoadingAnimation.visibility = View.GONE
                }
            }
        }

        animeViewModel.uiEvent.collectLatest { event ->
            when (event) {
            }
        }
    }
}