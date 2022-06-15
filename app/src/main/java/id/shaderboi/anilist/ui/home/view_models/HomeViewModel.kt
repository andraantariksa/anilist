package id.shaderboi.anilist.ui.home.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.anilist.core.domain.use_case.anime.AnimeUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    animeUseCases: AnimeUseCases
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<HomeUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val animeList = animeUseCases.listAnimeUseCase().cachedIn(viewModelScope)

    init {
        onEvent(HomeEvent.Load)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Load -> {}
        }
    }
}