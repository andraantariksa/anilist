package id.shaderboi.anilist.core.domain.model.anime_search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class To(
    @Json(name = "day")
    val day: Int?,
    @Json(name = "month")
    val month: Int?,
    @Json(name = "year")
    val year: Int?
)