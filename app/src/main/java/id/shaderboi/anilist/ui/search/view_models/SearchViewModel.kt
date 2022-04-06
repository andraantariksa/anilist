package id.shaderboi.anilist.ui.search.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.core.domain.use_case.anime.AnimeUseCases
import id.shaderboi.anilist.ui.util.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val animeUseCases: AnimeUseCases
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<SearchUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _searchResult: MutableSharedFlow<Flow<PagingData<AnimeData>>?> = MutableSharedFlow()
    val searchResult get() = _searchResult

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
            _searchResult.emit(
                animeUseCases.searchAnimeUseCase(query).cachedIn(viewModelScope)
            )
        }
    }
}