package id.shaderboi.anilist.ui.home.view_models

import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.ui.search.view_models.SearchUIEvent

sealed class HomeUIEvent {
    object Loading: HomeUIEvent()
    class Loaded(val anime: List<Anime>): HomeUIEvent()
    object LoadError: HomeUIEvent()
}