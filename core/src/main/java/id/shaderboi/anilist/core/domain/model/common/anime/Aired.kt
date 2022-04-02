package id.shaderboi.anilist.core.domain.model.common.anime

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Aired(
    @Json(name = "from")
    val from: String?,
    @Json(name = "prop")
    val prop: Prop?,
    @Json(name = "to")
    val to: String?,
    @Json(name = "string")
    val string: String?
)