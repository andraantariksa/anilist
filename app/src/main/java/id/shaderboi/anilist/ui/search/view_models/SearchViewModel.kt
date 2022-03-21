package id.shaderboi.anilist.ui.search.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {
    private val _uiEvent = MutableSharedFlow<SearchUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

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
                _uiEvent.emit(SearchUIEvent.Start)
            }
            return
        }

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            _uiEvent.emit(SearchUIEvent.Loading)
        }
    }
}