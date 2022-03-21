package id.shaderboi.anilist.ui.search.view_models

import id.shaderboi.anilist.core.domain.model.anime.Anime

sealed class SearchUIEvent {
    class Loaded(val anime: List<Anime>): SearchUIEvent()
    object Error: SearchUIEvent()
    object Loading: SearchUIEvent()
    object Start: SearchUIEvent()
}