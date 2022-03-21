package id.shaderboi.anilist.core.domain.model.anime_search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Jpg(
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "large_image_url")
    val largeImageUrl: String,
    @Json(name = "small_image_url")
    val smallImageUrl: String
)