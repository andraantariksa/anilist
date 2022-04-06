package id.shaderboi.anilist.favorite_anime.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.shaderboi.anilist.core.domain.use_case.favorite_anime.FavoriteAnimeUseCases
import id.shaderboi.anilist.ui.util.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteAnimeViewModel @Inject constructor(
    private val favoriteAnimeUseCases: FavoriteAnimeUseCases
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<FavoriteAnimeUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val favoriteAnimeList = favoriteAnimeUseCases.listFavoriteAnime().cachedIn(viewModelScope)

    init {
        onEvent(FavoriteAnimeEvent.Load)
    }

    fun onEvent(animeEvent: FavoriteAnimeEvent) {
        when (animeEvent) {
        }
    }
}