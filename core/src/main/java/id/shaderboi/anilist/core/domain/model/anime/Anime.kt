package id.shaderboi.anilist.core.domain.model.anime


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Anime(
    @Json(name = "data")
    val `data`: Data
)