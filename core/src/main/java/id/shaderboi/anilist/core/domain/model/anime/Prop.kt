package id.shaderboi.anilist.core.domain.model.anime


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Prop(
    @Json(name = "from")
    val from: From,
    @Json(name = "string")
    val string: String,
    @Json(name = "to")
    val to: To
)