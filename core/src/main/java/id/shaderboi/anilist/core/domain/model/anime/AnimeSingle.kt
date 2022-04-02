package id.shaderboi.anilist.core.domain.model.anime


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData

@JsonClass(generateAdapter = true)
data class AnimeSingle(
    @Json(name = "data")
    val animeData: AnimeData
)