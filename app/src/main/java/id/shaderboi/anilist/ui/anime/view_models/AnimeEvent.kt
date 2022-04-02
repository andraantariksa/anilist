package id.shaderboi.anilist.ui.anime.view_models

sealed class AnimeEvent {
    class Load(val id: Int): AnimeEvent()
    class ToggleAnimeFavorite(val id: Int): AnimeEvent()
}