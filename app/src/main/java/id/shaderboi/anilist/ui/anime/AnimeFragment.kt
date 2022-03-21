package id.shaderboi.anilist.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import id.shaderboi.anilist.databinding.FragmentAnimeBinding
import id.shaderboi.anilist.ui.anime.view_models.AnimeViewModel

class AnimeFragment : Fragment() {
    private val animeViewModel by viewModels<AnimeViewModel>()
    private var _binding: FragmentAnimeBinding? = null
    val binding get() = _binding!!

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
//        animeViewModel.uiEvent.collectLatest { event ->
//            when (event) {
//            }
//        }
    }
}