package id.shaderboi.anilist.ui.home.view_models

import id.shaderboi.anilist.ui.search.view_models.SearchEvent

sealed class HomeEvent {
    object Load: HomeEvent()
}