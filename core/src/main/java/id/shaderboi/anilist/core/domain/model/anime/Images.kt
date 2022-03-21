package id.shaderboi.anilist.core.domain.model.anime


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
    @Json(name = "jpg")
    val jpg: Jpg,
    @Json(name = "webp")
    val webp: Webp
)