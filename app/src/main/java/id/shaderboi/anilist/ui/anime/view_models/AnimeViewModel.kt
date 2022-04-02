package id.shaderboi.anilist.ui.anime.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.core.domain.use_case.anime.AnimeUseCases
import id.shaderboi.anilist.core.domain.use_case.favorite_anime.FavoriteAnimeUseCases
import id.shaderboi.anilist.ui.util.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeUseCases: AnimeUseCases,
    private val favoriteAnimeUseCases: FavoriteAnimeUseCases,
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<AnimeUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var animeJob: Job? = null

    private val _anime =
        MutableStateFlow<ResourceState<AnimeData, Throwable>>(ResourceState.Loading())
    val anime = _anime.asStateFlow()

    private val _isFavoriteAnime = MutableStateFlow(false)
    val isFavoriteAnime = _isFavoriteAnime.asStateFlow()

    fun onEvent(event: AnimeEvent) {
        when (event) {
            is AnimeEvent.Load -> animeLoad(event.id)
            is AnimeEvent.ToggleAnimeFavorite -> setAnimeAsFavorite(event.id)
        }
    }

    private fun setAnimeAsFavorite(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        if (isFavoriteAnime.value) {
            favoriteAnimeUseCases.deleteFavoriteAnime(id)
        } else {
            favoriteAnimeUseCases.addFavoriteAnime(id)
        }
    }

    private fun animeLoad(id: Int) {
        animeJob?.cancel()

        animeJob = viewModelScope.launch(Dispatchers.IO) {
            _anime.emit(ResourceState.Loading())
            val anime = animeUseCases.getAnimeDetailUseCase(id)
            if (anime.isSuccess) {
                _anime.emit(ResourceState.Loaded(anime.getOrNull()!!))
            } else {
                _anime.emit(ResourceState.Error(anime.exceptionOrNull()!!))
            }

            favoriteAnimeUseCases.getFavoriteAnime(id)
                .onEach {
                    _isFavoriteAnime.value = it != null
                }
                .launchIn(this)
        }
    }
}