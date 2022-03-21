package id.shaderboi.anilist.ui.anime.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.use_case.AnimeUseCases
import id.shaderboi.anilist.ui.util.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeUseCases: AnimeUseCases
): ViewModel() {
    private val _uiEvent = MutableSharedFlow<AnimeUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _anime = MutableStateFlow<ResourceState<Anime, Throwable>>(ResourceState.Loading())
    val anime = _anime.asStateFlow()

    fun onEvent(event: AnimeEvent) {
        when (event) {
            is AnimeEvent.Load -> animeLoad(event.id)
        }
    }

    private fun animeLoad(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        _anime.emit(ResourceState.Loading())
        val anime = animeUseCases.getAnimeDetailUseCase()
        if (anime.isSuccess) {
            _anime.emit(ResourceState.Loaded(anime.getOrNull()!!))
        } else {
            _anime.emit(ResourceState.Error(anime.exceptionOrNull()!!))
        }
    }
}