package id.shaderboi.anilist.ui.home.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.core.domain.use_case.anime.AnimeUseCases
import id.shaderboi.anilist.ui.util.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeUseCases: AnimeUseCases
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<HomeUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _animeList =
        MutableStateFlow<ResourceState<List<AnimeData>, Throwable>>(ResourceState.Loading())
    val animeList = _animeList.asStateFlow()

    init {
        onEvent(HomeEvent.Load)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Load -> loadAnime()
        }
    }

    private fun loadAnime() = viewModelScope.launch(Dispatchers.IO) {
        _animeList.emit(ResourceState.Loading())
        val animeList = animeUseCases.listAnimeUseCase()
        if (animeList.isSuccess) {
            _animeList.emit(ResourceState.Loaded(animeList.getOrNull()!!))
        } else {
            _animeList.emit(ResourceState.Error(animeList.exceptionOrNull()!!))
        }
    }
}