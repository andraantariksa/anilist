package id.shaderboi.anilist.core.data.model.anime_search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.shaderboi.anilist.core.data.model.common.anime.AnimeData
import id.shaderboi.anilist.core.domain.model.anime_search_result.AnimeSearchResult

@JsonClass(generateAdapter = true)
data class AnimeSearch(
    @Json(name = "data")
    val `data`: List<AnimeData>,
    @Json(name = "pagination")
    val pagination: Pagination
) {
    fun toAnimeSearchResult(): AnimeSearchResult {
        return AnimeSearchResult(
            animes = data.map { animeData -> animeData.toAnime() },
            pagination = id.shaderboi.anilist.core.domain.model.anime_search_result.Pagination(
                hasNextPage = pagination.hasNextPage,
                lastVisiblePage = pagination.lastVisiblePage,
            )
        )
    }
}