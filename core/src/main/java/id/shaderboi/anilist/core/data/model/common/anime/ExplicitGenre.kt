package id.shaderboi.anilist.core.data.model.common.anime

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExplicitGenre(
    @Json(name = "mal_id")
    val malId: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "type")
    val type: String?,
    @Json(name = "url")
    val url: String
)