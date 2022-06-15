package id.shaderboi.anilist.core.data.model.anime_search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pagination(
    @Json(name = "has_next_page")
    val hasNextPage: Boolean,
    @Json(name = "last_visible_page")
    val lastVisiblePage: Int
)