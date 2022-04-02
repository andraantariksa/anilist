package id.shaderboi.anilist.core.domain.model.common.anime

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Trailer(
    @Json(name = "embed_url")
    val embedUrl: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "youtube_id")
    val youtubeId: String?
)