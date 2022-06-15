package id.shaderboi.anilist.favorite_anime.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.shaderboi.anilist.core.domain.use_case.favorite_anime.FavoriteAnimeUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class FavoriteAnimeViewModel @Inject constructor(
    favoriteAnimeUseCases: FavoriteAnimeUseCases
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<FavoriteAnimeUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val favoriteAnimeList = favoriteAnimeUseCases.listFavoriteAnime().cachedIn(viewModelScope)

    init {
        onEvent(FavoriteAnimeEvent.Load)
    }

    fun onEvent(animeEvent: FavoriteAnimeEvent) {
        when (animeEvent) {
            FavoriteAnimeEvent.Load -> {}
        }
    }
}