package id.shaderboi.anilist.ui.search.view_models

sealed class SearchEvent {
    class EnqueueSearch(val query: String): SearchEvent()
}