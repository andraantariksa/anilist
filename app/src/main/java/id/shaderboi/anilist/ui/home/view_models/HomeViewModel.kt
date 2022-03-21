package id.shaderboi.anilist.ui.home.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ViewModelScoped
import id.shaderboi.anilist.ui.search.view_models.SearchEvent
import id.shaderboi.anilist.ui.search.view_models.SearchUIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@ViewModelScoped
class HomeViewModel : ViewModel() {
    private val _uiEvent = MutableSharedFlow<HomeUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        onEvent(HomeEvent.Load)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Load -> loadAnime()
        }
    }

    private fun loadAnime() = viewModelScope.launch(Dispatchers.IO) {
            _uiEvent.emit(HomeUIEvent.Loading)
//        _uiEvent.emit(HomeUIEvent.AnimeLoaded())
    }
}