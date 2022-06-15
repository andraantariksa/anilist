package id.shaderboi.anilist.core.domain.model.anime_search_result

import id.shaderboi.anilist.core.domain.model.anime.Anime

class AnimeSearchResult(
    val animes: List<Anime>,
    val pagination: Pagination
)