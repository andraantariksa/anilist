package id.shaderboi.anilist.ui.search.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.core.domain.use_case.anime.AnimeUseCases
import id.shaderboi.anilist.ui.util.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val animeUseCases: AnimeUseCases
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<SearchUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _searchResult =
        MutableStateFlow<ResourceState<List<AnimeData>, Throwable>?>(null)
    val searchResult = _searchResult.asStateFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.EnqueueSearch -> search(event.query)
        }
    }

    private fun search(query: String) {
        searchJob?.let { job ->
            if (job.isActive) {
                job.cancel()
            }
        }

        if (query.isBlank()) {
            viewModelScope.launch {
                _searchResult.emit(null)
            }
            return
        }

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            _searchResult.emit(ResourceState.Loading())

            val searchResult = animeUseCases.searchAnimeUseCase(query)
            if (searchResult.isSuccess) {
                _searchResult.emit(ResourceState.Loaded(searchResult.getOrNull()!!))
            } else {
                _searchResult.emit(ResourceState.Error(searchResult.exceptionOrNull()!!))
            }
        }
    }
}