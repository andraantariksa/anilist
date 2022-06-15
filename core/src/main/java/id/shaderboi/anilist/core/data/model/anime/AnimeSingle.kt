package id.shaderboi.anilist.core.data.model.anime


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.shaderboi.anilist.core.data.model.common.anime.AnimeData
import id.shaderboi.anilist.core.domain.model.anime.Anime

@JsonClass(generateAdapter = true)
data class AnimeSingle(
    @Json(name = "data")
    val animeData: AnimeData
) {
    fun toAnime(): Anime = animeData.toAnime()
}