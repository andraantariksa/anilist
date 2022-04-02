package id.shaderboi.anilist.favorite_anime.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.shaderboi.anilist.core.data.data_source_store.local.entities.FavoriteAnimeJoinedEntity
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

    private val _favoriteAnimeList =
        MutableStateFlow<ResourceState<List<FavoriteAnimeJoinedEntity>, Throwable>>(ResourceState.Loading())
    val favoriteAnimeList = _favoriteAnimeList.asStateFlow()

    init {
        onEvent(FavoriteAnimeEvent.Load)
    }

    fun onEvent(animeEvent: FavoriteAnimeEvent) {
        when (animeEvent) {
            FavoriteAnimeEvent.Load -> loadFavorite()
        }
    }

    private fun loadFavorite() = viewModelScope.launch(Dispatchers.Main) {
        _favoriteAnimeList.emit(ResourceState.Loading())
        favoriteAnimeUseCases.listFavoriteAnime()
            .onEach { favoriteAnime ->
                _favoriteAnimeList.emit(
                    ResourceState.Loaded(favoriteAnime)
                )
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}