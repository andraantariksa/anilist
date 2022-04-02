package id.shaderboi.anilist.core.domain.model.anime_search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData

@JsonClass(generateAdapter = true)
data class AnimeSearch(
    @Json(name = "data")
    val `data`: List<AnimeData>,
    @Json(name = "pagination")
    val pagination: Pagination
)