package id.shaderboi.anilist.ui.search

import android.os.Bundle
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.databinding.FragmentSearchBinding
import id.shaderboi.anilist.ui.search.view_models.SearchEvent
import id.shaderboi.anilist.ui.search.view_models.SearchUIEvent
import id.shaderboi.anilist.ui.search.view_models.SearchViewModel
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
        searchViewModel.uiEvent.collectLatest { event ->
            when (event) {
                SearchUIEvent.Start -> {
                    binding.recyclerViewSearchedAnime.visibility = View.GONE
                    binding.linearLayoutSearchAnimation.visibility = View.GONE
                    binding.linearLayoutIdleImage.visibility = View.VISIBLE
                }
                SearchUIEvent.Loading -> {
                    binding.recyclerViewSearchedAnime.visibility = View.GONE
                    binding.linearLayoutIdleImage.visibility = View.GONE
                    binding.linearLayoutSearchAnimation.visibility = View.VISIBLE
                }
                SearchUIEvent.Error -> {
                    binding.recyclerViewSearchedAnime.visibility = View.GONE
                    binding.linearLayoutSearchAnimation.visibility = View.GONE
                    binding.linearLayoutIdleImage.visibility = View.GONE
                }
                is SearchUIEvent.Loaded -> {
                    binding.recyclerViewSearchedAnime.visibility = View.VISIBLE
                    binding.linearLayoutSearchAnimation.visibility = View.GONE
                    binding.linearLayoutIdleImage.visibility = View.GONE
                }
            }
        }
    }
}