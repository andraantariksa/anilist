package id.shaderboi.anilist.core.domain.model.anime_search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnimeSearch(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "pagination")
    val pagination: Pagination
)